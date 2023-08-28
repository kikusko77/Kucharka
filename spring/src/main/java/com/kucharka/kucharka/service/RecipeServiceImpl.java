package com.kucharka.kucharka.service;

import com.kucharka.kucharka.DTO.RecipeDTO;
import com.kucharka.kucharka.DTO.RecipeGroceryDTO;
import com.kucharka.kucharka.entity.Grocery;
import com.kucharka.kucharka.entity.Recipe;
import com.kucharka.kucharka.entity.RecipeGrocery;
import com.kucharka.kucharka.entity.User;
import com.kucharka.kucharka.repository.GroceryRepository;
import com.kucharka.kucharka.repository.RecipeGroceryRepository;
import com.kucharka.kucharka.repository.RecipeRepository;
import com.kucharka.kucharka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // Get the authenticated user's username from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        User existingUser = userRepository.findByName(username)
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
    public List<RecipeDTO> getAllRecipesByUserId(Long userId) {
        // Get the authenticated user's username from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User authenticatedUser = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the authenticated user's ID matches the user ID parameter
        if (!authenticatedUser.getId().equals(userId)) {
            throw new IllegalArgumentException("You do not have permission to access these recipes.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Recipe> recipes = recipeRepository.findAllByUser(user);
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

        // Get the authenticated user's username from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User authenticatedUser = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the authenticated user is the owner of the recipe
        if (!existingRecipe.getUser().getId().equals(authenticatedUser.getId())) {
            throw new IllegalArgumentException("You do not have permission to update this recipe.");
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
            existingRecipe.setName(recipeDTO.getName());
            existingRecipe.setSellingPrice(recipeDTO.getSellingPrice());


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
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Get the authenticated user's username from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        User authenticatedUser = userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the authenticated user is the owner of the recipe
        if (!existingRecipe.getUser().getId().equals(authenticatedUser.getId())) {
            throw new IllegalArgumentException("You do not have permission to delete this recipe.");
        }

        recipeRepository.deleteById(id);
    }


}
