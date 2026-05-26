package com.recipeapp.service;

import com.recipeapp.model.Ingredient;
import com.recipeapp.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.PredefinedScopeConfigurationImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {
    //dependency injection
    private final IngredientRepository ingredientRepository;

    //getAll
    public Page<Ingredient> getAllIngredients(Pageable pageable){
        return ingredientRepository.findAll(pageable);
    }

    //filtering
    public Page<Ingredient> searchIngredients(String name, String type, Pageable pageable){
        boolean hasName = name != null && !name.isBlank();
        boolean hasType = type != null && !type.isBlank();

        if(hasName && hasType){
            return ingredientRepository.findByNameContainingIgnoreCaseAndTypeContainingIgnoreCase(name, type, pageable);
        } else if (hasName) {
            return ingredientRepository.findByNameContainingIgnoreCase(name, pageable);
        }else if (hasType){
            return ingredientRepository.findByTypeContainingIgnoreCase(type, pageable);
        }else{
            return ingredientRepository.findAll(pageable);
        }
    }

    //getById
    public Ingredient getIngredientById(Long id){
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + id));
    }

    //save
    public Ingredient saveIngredient(Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }

    //delete
    public void deleteIngredient(Long id){
        if(!ingredientRepository.existsById(id)){
            throw new EntityNotFoundException("Ingredient not found with id: " + id);
        }

        ingredientRepository.deleteById(id);
    }


}
