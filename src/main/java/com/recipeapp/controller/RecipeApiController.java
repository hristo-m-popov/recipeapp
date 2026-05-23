package com.recipeapp.controller;

import com.recipeapp.model.Recipe;
import com.recipeapp.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeApiController {
    // dependency injection
    private final RecipeService recipeService;

    //getAll
    @GetMapping
    public ResponseEntity<Page<Recipe>> getAll(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(recipeService.searchRecipes(title, type, pageable));
    }


    //getById
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable Long id){
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }


    //create
    @PostMapping
    public ResponseEntity<Recipe> create(@RequestBody Recipe recipe){
        return ResponseEntity.ok(recipeService.saveRecipe(recipe));
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody Recipe recipe){
        recipe.setId(id);
        return ResponseEntity.ok(recipeService.saveRecipe(recipe));
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
