package com.recipeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 150)
    private String name;

    @NotNull
    @Column(nullable = false, length = 150)
    private String type;

    @Column(nullable = false)
    private LocalDate createdAt = LocalDate.now();
}
