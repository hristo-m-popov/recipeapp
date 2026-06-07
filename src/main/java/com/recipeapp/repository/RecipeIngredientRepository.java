package com.recipeapp.repository;

import com.recipeapp.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    List<RecipeIngredient> findByIngredient_Id(Long ingredientId);

    List<RecipeIngredient> findByRecipe_Id(Long recipeId);
}
