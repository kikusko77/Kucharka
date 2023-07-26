package com.kucharka.Kucharka.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kucharka.Kucharka.roles.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name",nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "recipe_grocery",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "grocery_id"))
    @JsonManagedReference
    private List<Grocery> groceries;


}

