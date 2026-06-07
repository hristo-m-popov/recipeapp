package com.recipeapp.controller;

import com.recipeapp.model.Category;
import com.recipeapp.model.Ingredient;
import com.recipeapp.service.IngredientService;
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
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Ingredient> ingredients = ingredientService.searchIngredients(name, type, pageable);

        model.addAttribute("ingredients", ingredients);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        return "ingredients/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.getIngredientById(id));
        return "ingredients/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Ingredient ingredient,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            return "ingredients/form";
        }

        ingredientService.saveIngredient(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return "redirect:/ingredients";
    }
}