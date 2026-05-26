package com.recipeapp.service;

import com.recipeapp.model.Category;
import com.recipeapp.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    //getAll
    public Page<Category> getAllCategories(Pageable pageable){
        return categoryRepository.findAll(pageable);
    }

    //filtering(search)
    public Page<Category> searchCategories(String name, Pageable pageable){
        boolean hasName = name != null && !name.isBlank();

        if(hasName) {
            return categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            return categoryRepository.findAll(pageable);
        }
    }

    //getById
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    //save
    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    //delete
    public void deleteCategory(Long id){
        if(!categoryRepository.existsById(id)){
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}