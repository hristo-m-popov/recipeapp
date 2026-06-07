package com.recipeapp.controller;

import com.recipeapp.model.RecipeIngredient;
import com.recipeapp.repository.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-ingredients")
@RequiredArgsConstructor
public class RecipeIngredientApiController {

    private final RecipeIngredientRepository recipeIngredientRepository;

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<RecipeIngredient>> getByRecipe(@PathVariable Long recipeId) {
        return ResponseEntity.ok(recipeIngredientRepository.findByRecipe_Id(recipeId));
    }

    @PostMapping
    public ResponseEntity<RecipeIngredient> create(@RequestBody RecipeIngredient ri) {
        return ResponseEntity.ok(recipeIngredientRepository.save(ri));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeIngredientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}