package com.recipeapp.repository;

import com.recipeapp.model.Category;
import com.recipeapp.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Recipe> findByCategoryContainingIgnoreCase(Category category, Pageable pageable);
    Page<Recipe> findByTitleContainingIgnoreCaseAndCategoryContainingIgnoreCase
            (String title, Category category, Pageable pageable);

}
