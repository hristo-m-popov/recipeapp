package com.recipeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String steps;

    @Min(1)
    @Max(10)
    private Integer portions;

    private Double price;

    private Integer rating;

    private Boolean favorite = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


}
