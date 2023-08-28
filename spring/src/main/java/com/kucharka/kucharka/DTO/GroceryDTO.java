package com.kucharka.kucharka.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter
public class GroceryDTO {
    private Long id;
    private String name;
    private BigDecimal gross;
    private BigDecimal clean;
}
