package com.yura.workshop.repositories;

import com.yura.workshop.db.DatabaseConnection;
import com.yura.workshop.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, description FROM categories ORDER BY id";
        try (Connection conn = DatabaseConnection.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categories.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching categories", e);
        }
        return categories;
    }

    @Override
    public Optional<Category> findById(int id) {
        String sql = "SELECT id, name, description FROM categories WHERE id = ?";
        try (Connection conn = DatabaseConnection.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching category by id", e);
        }
        return Optional.empty();
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description")
        );
    }
}
