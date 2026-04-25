package com.yura.workshop.models;

import java.math.BigDecimal;

public class InventorySummary {

    private int totalProducts;
    private BigDecimal totalValue;
    private String mostExpensiveProduct;
    private BigDecimal highestPrice;
    private String highestStockProduct;
    private int maxStock;

    public InventorySummary() {
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getMostExpensiveProduct() {
        return mostExpensiveProduct;
    }

    public void setMostExpensiveProduct(String mostExpensiveProduct) {
        this.mostExpensiveProduct = mostExpensiveProduct;
    }

    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getHighestStockProduct() {
        return highestStockProduct;
    }

    public void setHighestStockProduct(String highestStockProduct) {
        this.highestStockProduct = highestStockProduct;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }
}
