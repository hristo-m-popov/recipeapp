package com.recipeapp.service;

import com.recipeapp.model.Recipe;
import com.recipeapp.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    // dependency injection
    private final RecipeRepository recipeRepository;

    //getAllRecipes
    public Page<Recipe> getAllRecipes(Pageable pageable){
        return recipeRepository.findAll(pageable);
    }

    //filtering
    public Page<Recipe> searchRecipes(String title, String type, Pageable pageable){
        boolean hasTitle = title != null && !title.isBlank();
        boolean hasType = type != null && !type.isBlank();

        if(hasTitle && hasType){
            return recipeRepository.findByTitleContainingIgnoreCaseAndTypeContainingIgnoreCase(title, type, pageable);
        } else if(hasTitle){
            return recipeRepository.findByTitleContainingIgnoreCase(title, pageable);
        }else if(hasType){
            return recipeRepository.findByTypeContainingIgnoreCase(type, pageable);
        }else{
            return recipeRepository.findAll(pageable);
        }
    }

    public Recipe getRecipeById(Long id){
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));
    }

    //save
    public Recipe saveRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    //delete
    public void deleteRecipe(Long id){
        if(!recipeRepository.existsById(id)){
            throw new EntityNotFoundException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }
}
