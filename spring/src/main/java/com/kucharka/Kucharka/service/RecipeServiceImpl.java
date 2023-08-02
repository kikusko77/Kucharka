package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.entity.Grocery;
import com.kucharka.Kucharka.entity.Recipe;
import com.kucharka.Kucharka.entity.RecipeGrocery;
import com.kucharka.Kucharka.entity.User;
import com.kucharka.Kucharka.repository.GroceryRepository;
import com.kucharka.Kucharka.repository.RecipeGroceryRepository;
import com.kucharka.Kucharka.repository.RecipeRepository;
import com.kucharka.Kucharka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final GroceryRepository groceryRepository;
    private final RecipeGroceryRepository recipeGroceryRepository;




    @Override
    public Recipe addRecipe(Recipe recipe) {
        User user = userRepository.findById(recipe.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        recipe.setUser(user);

        List<RecipeGrocery> recipeGroceries = new ArrayList<>();
        for (RecipeGrocery rg : recipe.getRecipeGroceries()) {
            Grocery grocery = groceryRepository.findById(rg.getGrocery().getId())
                    .orElseThrow(() -> new RuntimeException("Grocery not found"));
            rg.setGrocery(grocery);
            rg.setRecipe(recipe);
            recipeGroceries.add(rg);
        }
        recipe.setRecipeGroceries(recipeGroceries);

        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        User user = userRepository.findById(recipe.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        recipe.setUser(user);

        Recipe updatedRecipe = recipeRepository.save(recipe);

        for (RecipeGrocery recipeGrocery : recipe.getRecipeGroceries()) {
            Grocery grocery = groceryRepository.findById(recipeGrocery.getGrocery().getId())
                    .orElseThrow(() -> new RuntimeException("Grocery not found"));

            recipeGrocery.setRecipe(updatedRecipe);
            recipeGrocery.setGrocery(grocery);

            recipeGroceryRepository.save(recipeGrocery);
        }

        return updatedRecipe;
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}