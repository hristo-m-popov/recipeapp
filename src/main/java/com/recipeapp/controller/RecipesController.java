package com.recipeapp.controller;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;
import com.recipeapp.model.RecipeIngredient;
import com.recipeapp.repository.RecipeIngredientRepository;
import com.recipeapp.service.CategoryService;
import com.recipeapp.service.IngredientService;
import com.recipeapp.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientService ingredientService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Recipe> recipes = recipeService.searchRecipes(title, categoryName, pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("title", title);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("categories", categoryService.getAllCategories(
                PageRequest.of(0, 100)).getContent());
        return "recipes/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("categories", categoryService.getAllCategories(
                PageRequest.of(0, 100)).getContent());
        return "recipes/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        model.addAttribute("categories", categoryService.getAllCategories(
                PageRequest.of(0, 100)).getContent());
        return "recipes/form";
    }

    @PostMapping("/{id}/ingredients/add")
    public String addIngredient(@PathVariable Long id,
                                @RequestParam Long ingredientId,
                                @RequestParam(required = false) Double quantity,
                                @RequestParam(required = false) String unit) {

        RecipeIngredient ri = new RecipeIngredient();
        ri.setRecipe(recipeService.getRecipeById(id));

        Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
        ri.setIngredient(ingredient);
        ri.setQuantity(quantity);
        ri.setUnit(unit);

        recipeIngredientRepository.save(ri);
        return "redirect:/recipes/" + id;
    }

    @GetMapping("/{recipeId}/ingredients/{riId}/delete")
    public String removeIngredient(@PathVariable Long recipeId,
                                   @PathVariable Long riId) {
        recipeIngredientRepository.deleteById(riId);
        return "redirect:/recipes/" + recipeId;
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Recipe recipe,
                       BindingResult result,
                       @RequestParam(value = "favorite", required = false) String favoriteParam,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories(
                    PageRequest.of(0, 100)).getContent());
            return "recipes/form";
        }
        recipe.setFavorite(favoriteParam != null && favoriteParam.equals("true"));

        if (recipe.getCategory() != null && recipe.getCategory().getId() == null) {
            recipe.setCategory(null);
        }

        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/recipes";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        model.addAttribute("recipeIngredients",
                recipeIngredientRepository.findByRecipe_Id(id));
        model.addAttribute("allIngredients",
                ingredientService.getAllIngredients(PageRequest.of(0, 100)).getContent());
        return "recipes/detail";
    }
}