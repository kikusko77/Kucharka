package com.kucharka.Kucharka.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true)
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
    @JsonBackReference
    private List<Recipe> recipes;
}
