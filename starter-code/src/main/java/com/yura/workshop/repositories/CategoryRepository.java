package com.yura.workshop.repositories;

import com.yura.workshop.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> findAll();

    Optional<Category> findById(int id);
}
