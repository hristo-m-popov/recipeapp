package com.recipeapp.controller;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.RecipeIngredient;
import com.recipeapp.repository.RecipeIngredientRepository;
import com.recipeapp.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final IngredientService ingredientService;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @GetMapping("/")
    public String home(Model model){
        List<Ingredient> ingredients = ingredientService
                .getAllIngredients(PageRequest.of(0, 100)).getContent();

        Map<Long, List<RecipeIngredient>> recipeMap = new HashMap<>();
        for(Ingredient ingredient : ingredients){
            List<RecipeIngredient> recipes = recipeIngredientRepository
                    .findByIngredient_Id(ingredient.getId());
            recipeMap.put(ingredient.getId(), recipes);
        }

        model.addAttribute("ingredients", ingredients);
        model.addAttribute("recipeMap", recipeMap);
        return "index";
    }
}
