package com.recipeapp.controller;

import com.recipeapp.model.Ingredient;
import com.recipeapp.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
public class IngredientApiController {
    private final IngredientService ingredientService;

    //getAll
    @GetMapping
    public ResponseEntity<Page<Ingredient>> getAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(ingredientService.searchIngredients(name, type, pageable));
    }

    //getById
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getById(@PathVariable Long id){
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    //create
    @PostMapping
    public ResponseEntity<Ingredient> create(@RequestBody Ingredient ingredient){
        return ResponseEntity.ok(ingredientService.saveIngredient(ingredient));
    }

    //update
    @PostMapping("/{id}")
    public ResponseEntity<Ingredient> update(@PathVariable Long id, @RequestBody Ingredient ingredient){
        ingredient.setId(id);
        return ResponseEntity.ok(ingredientService.saveIngredient(ingredient));
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
