package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.entity.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);
    Recipe getRecipeById(Long id);
    List<Recipe> getAllRecipes();
    Recipe updateRecipe(Recipe recipe);
    void deleteRecipe(Long id);
}
