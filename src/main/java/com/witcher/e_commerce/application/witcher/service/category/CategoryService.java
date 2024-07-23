package com.witcher.e_commerce.application.witcher.service.category;

import com.witcher.e_commerce.application.witcher.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    public void addCategory(Category category);

    public void deleteCategoryById(Long id);

    public Optional<Category> getCategoryById(Long id);
}
