package com.kucharka.Kucharka.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter
public class RecipeGroceryDTO {
    private Long groceryId;
    private BigDecimal priceForKg;
    private BigDecimal weight;
}
