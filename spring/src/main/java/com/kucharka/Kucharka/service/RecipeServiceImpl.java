package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.entity.Grocery;
import com.kucharka.Kucharka.entity.Recipe;
import com.kucharka.Kucharka.entity.User;
import com.kucharka.Kucharka.repository.GroceryRepository;
import com.kucharka.Kucharka.repository.RecipeRepository;
import com.kucharka.Kucharka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final GroceryRepository groceryRepository;




    @Override
    public Recipe addRecipe(Recipe recipe) {
        User user = userRepository.findById(recipe.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Grocery> groceries = groceryRepository.findAllById(
                recipe.getGroceries().stream()
                        .map(Grocery::getId)
                        .collect(Collectors.toList())
        );
        recipe.setUser(user);
        recipe.setGroceries(groceries);

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

        List<Grocery> groceries = groceryRepository.findAllById(
                recipe.getGroceries().stream()
                        .map(Grocery::getId)
                        .collect(Collectors.toList())
        );

        recipe.setUser(user);
        recipe.setGroceries(groceries);


        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}