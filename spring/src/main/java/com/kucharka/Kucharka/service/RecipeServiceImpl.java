package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.DTO.RecipeDTO;
import com.kucharka.Kucharka.DTO.RecipeGroceryDTO;
import com.kucharka.Kucharka.entity.Grocery;
import com.kucharka.Kucharka.entity.Recipe;
import com.kucharka.Kucharka.entity.RecipeGrocery;
import com.kucharka.Kucharka.entity.User;
import com.kucharka.Kucharka.repository.GroceryRepository;
import com.kucharka.Kucharka.repository.RecipeGroceryRepository;
import com.kucharka.Kucharka.repository.RecipeRepository;
import com.kucharka.Kucharka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final GroceryRepository groceryRepository;
    private final RecipeGroceryRepository recipeGroceryRepository;
    private final ModelMapper modelMapper;

    @Override
    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = modelMapper.map(recipeDTO, Recipe.class);
        User user = recipe.getUser();
        if (user == null) {
            throw new RuntimeException("User information is missing in the recipe");
        }

        Long userId = user.getId();
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        recipe.setUser(existingUser);

        Map<Long, RecipeGrocery> uniqueRecipeGroceries = new HashMap<>();
        for (RecipeGrocery rg : recipe.getRecipeGroceries()) {
            Grocery grocery = groceryRepository.findById(rg.getGrocery().getId())
                    .orElseThrow(() -> new RuntimeException("Grocery not found"));
            rg.setGrocery(grocery);
            rg.setRecipe(recipe);
            uniqueRecipeGroceries.put(grocery.getId(), rg);
        }
        recipe.setRecipeGroceries(new ArrayList<>(uniqueRecipeGroceries.values()));

        return modelMapper.map(recipeRepository.save(recipe), RecipeDTO.class);
    }

    @Override
    public RecipeDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        RecipeDTO recipeDTO = modelMapper.map(recipe, RecipeDTO.class);

        for (int i = 0; i < recipe.getRecipeGroceries().size(); i++) {
            recipeDTO.getRecipeGroceries().get(i).setGroceryId(recipe.getRecipeGroceries().get(i).getGrocery().getId()); // Set the grocery IDs
        }

        return recipeDTO;
    }


    @Override
    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(recipe -> {
                    RecipeDTO recipeDTO = modelMapper.map(recipe, RecipeDTO.class);

                    for (int i = 0; i < recipe.getRecipeGroceries().size(); i++) {
                        recipeDTO.getRecipeGroceries().get(i).setGroceryId(recipe.getRecipeGroceries().get(i).getGrocery().getId()); // Set the grocery IDs
                    }
                    return recipeDTO;
                })
                .collect(Collectors.toList());
    }



    @Override
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        Recipe existingRecipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Update the properties of the existing Recipe entity
        existingRecipe.setName(recipeDTO.getName());
        existingRecipe.setSellingPrice(recipeDTO.getSellingPrice());

        Long userId = recipeDTO.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("RecipeDTO must have a valid userId.");
        }

        // Check if the user ID in the RecipeDTO matches the existing Recipe's user ID
        if (!existingRecipe.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Cannot change the user of an existing Recipe.");
        }

        // Update the RecipeGrocery entities
        Map<Long, RecipeGrocery> updatedRecipeGroceries = new HashMap<>();
        for (RecipeGroceryDTO rgDTO : recipeDTO.getRecipeGroceries()) {
            Long groceryId = rgDTO.getGroceryId();
            RecipeGrocery existingRG = existingRecipe.getRecipeGroceries().stream()
                    .filter(rg -> rg.getGrocery().getId().equals(groceryId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("RecipeGrocery not found"));

            // Update the properties of the existing RecipeGrocery entity
            existingRG.setPriceForKg(rgDTO.getPriceForKg());
            existingRG.setWeight(rgDTO.getWeight());

            updatedRecipeGroceries.put(groceryId, existingRG);
        }

        // Remove RecipeGrocery entities that are not present in the RecipeDTO
        Iterator<RecipeGrocery> iterator = existingRecipe.getRecipeGroceries().iterator();
        while (iterator.hasNext()) {
            RecipeGrocery existingRG = iterator.next();
            if (!updatedRecipeGroceries.containsKey(existingRG.getGrocery().getId())) {
                iterator.remove();
                recipeGroceryRepository.delete(existingRG);
            }
        }

        // Associate RecipeGroceries with the existing Recipe and save them
        for (RecipeGrocery rg : updatedRecipeGroceries.values()) {
            rg.setRecipe(existingRecipe); // Associate RecipeGrocery with Recipe
            recipeGroceryRepository.save(rg);
        }

        // Save the updated Recipe
        Recipe updatedRecipe = recipeRepository.save(existingRecipe);

        return modelMapper.map(updatedRecipe, RecipeDTO.class);
    }
    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

}
