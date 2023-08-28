package com.kucharka.kucharka.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kucharka.kucharka.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter

public class RecipeDTO {
    private Long id;
    private String name;
    private BigDecimal sellingPrice;
    private Long userId;
    @JsonIgnore
    private User user;
    private List<RecipeGroceryDTO> recipeGroceries;

}
