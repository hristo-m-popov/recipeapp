package com.recipeapp.repository;

import com.recipeapp.model.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Page<Ingredient> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Ingredient> findByTypeContainingIgnoreCase(String type, Pageable pageable);
    Page<Ingredient> findByNameContainingIgnoreCaseAndTypeContainingIgnoreCase
            (String name, String type, Pageable pageable);
}
