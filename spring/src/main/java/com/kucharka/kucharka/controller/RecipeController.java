package com.kucharka.kucharka.controller;


import com.kucharka.kucharka.DTO.RecipeDTO;
import com.kucharka.kucharka.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDTO> addRecipe(@RequestBody RecipeDTO recipeDTO) {
        RecipeDTO newRecipe = recipeService.addRecipe(recipeDTO);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<RecipeDTO>> getAllRecipesByUserId(@PathVariable Long userId) {
       List<RecipeDTO> recipes = recipeService.getAllRecipesByUserId(userId);
       return new ResponseEntity<>(recipes, HttpStatus.OK);
    }




    @PutMapping
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDTO) {
        RecipeDTO updatedRecipe = recipeService.updateRecipe(recipeDTO);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

