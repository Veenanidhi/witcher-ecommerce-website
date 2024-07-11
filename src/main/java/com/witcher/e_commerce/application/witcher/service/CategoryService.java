package com.witcher.e_commerce.application.witcher.service;

import com.witcher.e_commerce.application.witcher.dao.CategoryRepository;
import com.witcher.e_commerce.application.witcher.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    public void addCategory(Category category){

        categoryRepository.save(category);

    }
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public void removeCategoryById(int id){
        categoryRepository.deleteById(id);
    }
    public Optional<Category> getCategoryById(int id){
        return categoryRepository.findById(id);
    }


}
