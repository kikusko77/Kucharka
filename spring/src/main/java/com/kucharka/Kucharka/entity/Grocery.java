package com.kucharka.Kucharka.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "grocery")
public class Grocery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name",nullable = false)
    private String name;

    @NonNull
    @Column(name = "gross",nullable = false)
    private BigDecimal gross;

    @NonNull
    @Column(name = "clean",nullable = false)
    private BigDecimal clean;

    @ManyToMany(mappedBy = "groceries")
    private List<Recipe> recipes;




}
