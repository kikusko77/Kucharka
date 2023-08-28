package com.kucharka.kucharka.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter
@Entity
@Table(name = "recipe")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name",nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeGrocery> recipeGroceries;

}
