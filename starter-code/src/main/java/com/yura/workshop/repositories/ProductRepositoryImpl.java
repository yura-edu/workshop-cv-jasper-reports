package com.yura.workshop.repositories;

import com.yura.workshop.db.DatabaseConnection;
import com.yura.workshop.models.InventorySummary;
import com.yura.workshop.models.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description, price, stock, category_id, created_at FROM products ORDER BY id";
        try (Connection conn = DatabaseConnection.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching products", e);
        }
        return products;
    }

    @Override
    public List<Product> findByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description, price, stock, category_id, created_at "
                + "FROM products WHERE category_id = ? ORDER BY id";
        try (Connection conn = DatabaseConnection.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching products by category", e);
        }
        return products;
    }

    @Override
    public List<Product> findByFilters(LocalDate from, LocalDate to, int minStock) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT id, name, description, price, stock, category_id, created_at FROM products WHERE 1=1");
        if (from != null) {
            sql.append(" AND created_at >= ?");
        }
        if (to != null) {
            sql.append(" AND created_at < ?");
        }
        if (minStock > 0) {
            sql.append(" AND stock >= ?");
        }
        sql.append(" ORDER BY name");

        try (Connection conn = DatabaseConnection.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (from != null) {
                ps.setObject(idx++, from.atStartOfDay());
            }
            if (to != null) {
                ps.setObject(idx++, to.plusDays(1).atStartOfDay());
            }
            if (minStock > 0) {
                ps.setInt(idx, minStock);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching filtered products", e);
        }
        return products;
    }

    @Override
    public InventorySummary getSummary() {
        String sql = """
                SELECT
                    COUNT(*)                            AS total_products,
                    COALESCE(SUM(price * stock), 0)     AS total_value,
                    (SELECT name FROM products ORDER BY price DESC LIMIT 1)  AS most_expensive,
                    (SELECT price FROM products ORDER BY price DESC LIMIT 1) AS highest_price,
                    (SELECT name FROM products ORDER BY stock DESC LIMIT 1)  AS highest_stock_name,
                    (SELECT stock FROM products ORDER BY stock DESC LIMIT 1) AS max_stock
                FROM products
                """;
        try (Connection conn = DatabaseConnection.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                InventorySummary summary = new InventorySummary();
                summary.setTotalProducts(rs.getInt("total_products"));
                summary.setTotalValue(rs.getBigDecimal("total_value"));
                summary.setMostExpensiveProduct(rs.getString("most_expensive"));
                summary.setHighestPrice(rs.getBigDecimal("highest_price"));
                summary.setHighestStockProduct(rs.getString("highest_stock_name"));
                summary.setMaxStock(rs.getInt("max_stock"));
                return summary;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching inventory summary", e);
        }
        InventorySummary empty = new InventorySummary();
        empty.setTotalValue(BigDecimal.ZERO);
        return empty;
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setStock(rs.getInt("stock"));
        p.setCategoryId(rs.getInt("category_id"));
        java.sql.Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            p.setCreatedAt(ts.toLocalDateTime());
        }
        return p;
    }
}
