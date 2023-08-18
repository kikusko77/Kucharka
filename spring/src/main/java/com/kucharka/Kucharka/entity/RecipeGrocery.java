package com.kucharka.Kucharka.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter
@Entity
@Table(name = "recipe_grocery")

public class RecipeGrocery {
    @Id
    @JsonIgnore
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    private Recipe recipe;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "grocery_id")
    private Grocery grocery;

    private BigDecimal priceForKg;
    private BigDecimal weight;


}
