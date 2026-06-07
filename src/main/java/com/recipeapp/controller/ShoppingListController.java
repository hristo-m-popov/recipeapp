package com.recipeapp.controller;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.ShoppingList;
import com.recipeapp.service.IngredientService;
import com.recipeapp.service.ShoppingListService;
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
@RequestMapping("/shopping")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final IngredientService ingredientService;

    @GetMapping
    public String list(
            @RequestParam(required = false) Boolean purchased,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            Model model) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ShoppingList> items = purchased != null
                ? shoppingListService.getItemsByPurchased(purchased, pageable)
                : shoppingListService.getAllItems(pageable);

        model.addAttribute("items", items);
        model.addAttribute("purchased", purchased);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        return "shopping/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("item", new ShoppingList());
        model.addAttribute("ingredients", ingredientService.getAllIngredients(
                PageRequest.of(0, 100)).getContent());
        return "shopping/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", shoppingListService.getItemById(id));
        model.addAttribute("ingredients", ingredientService.getAllIngredients(
                PageRequest.of(0, 100)).getContent());
        return "shopping/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute ShoppingList item,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.getAllIngredients(
                    PageRequest.of(0, 100)).getContent());
            return "shopping/form";
        }
        shoppingListService.saveItem(item);
        return "redirect:/shopping";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        shoppingListService.deleteItem(id);
        return "redirect:/shopping";
    }

    @GetMapping("/{id}/purchased")
    public String markPurchased(@PathVariable Long id) {
        shoppingListService.markAsPurchased(id);
        return "redirect:/shopping";
    }
}