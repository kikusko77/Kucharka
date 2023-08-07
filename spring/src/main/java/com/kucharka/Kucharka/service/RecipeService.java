package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.DTO.RecipeDTO;
import com.kucharka.Kucharka.entity.Recipe;

import java.util.List;

public interface RecipeService {


    RecipeDTO addRecipe(RecipeDTO recipeDTO);
    RecipeDTO getRecipeById(Long id);
    List<RecipeDTO> getAllRecipes();
    RecipeDTO updateRecipe(RecipeDTO recipeDTO);
    void deleteRecipe(Long id);
}
