package com.recipeapp.repository;

import com.recipeapp.model.ShoppingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    Page<ShoppingList> findByPurchased(Boolean purchased, Pageable pageable);
}
