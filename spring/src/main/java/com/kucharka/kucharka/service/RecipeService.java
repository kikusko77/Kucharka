package com.kucharka.kucharka.service;

import com.kucharka.kucharka.DTO.RecipeDTO;

import java.util.List;

public interface RecipeService {


    RecipeDTO addRecipe(RecipeDTO recipeDTO);


    List<RecipeDTO> getAllRecipesByUserId(Long userId);

    RecipeDTO updateRecipe(RecipeDTO recipeDTO);
    void deleteRecipe(Long id);
}
