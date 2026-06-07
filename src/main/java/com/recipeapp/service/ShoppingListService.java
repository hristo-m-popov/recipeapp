package com.recipeapp.service;

import com.recipeapp.model.ShoppingList;
import com.recipeapp.repository.ShoppingListRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public Page<ShoppingList> getAllItems(Pageable pageable) {
        return shoppingListRepository.findAll(pageable);
    }

    public Page<ShoppingList> getItemsByPurchased(Boolean purchased, Pageable pageable) {
        return shoppingListRepository.findByPurchased(purchased, pageable);
    }

    public ShoppingList getItemById(Long id) {
        return shoppingListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping list item not found with id: " + id));
    }

    public ShoppingList saveItem(ShoppingList item) {
        return shoppingListRepository.save(item);
    }

    public void deleteItem(Long id) {
        if (!shoppingListRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Shopping list item not found with id: " + id);
        }
        shoppingListRepository.deleteById(id);
    }

    public void markAsPurchased(Long id) {
        ShoppingList item = getItemById(id);
        item.setPurchased(true);
        shoppingListRepository.save(item);
    }
}