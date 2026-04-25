package com.yura.workshop.repositories;

import com.yura.workshop.models.InventorySummary;
import com.yura.workshop.models.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    List<Product> findByCategoryId(int categoryId);

    List<Product> findByFilters(LocalDate from, LocalDate to, int minStock);

    InventorySummary getSummary();
}
