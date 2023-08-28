package com.kucharka.kucharka.service;

import com.kucharka.kucharka.DTO.GroceryDTO;
import com.kucharka.kucharka.entity.Grocery;
import com.kucharka.kucharka.entity.RecipeGrocery;
import com.kucharka.kucharka.repository.GroceryRepository;
import com.kucharka.kucharka.repository.RecipeGroceryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GroceryServiceImpl implements GroceryService {

    private final GroceryRepository groceryRepository;
    private final RecipeGroceryRepository recipeGroceryRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<GroceryDTO> getAllGroceries() {
        return groceryRepository.findAll().stream()
                .map(grocery -> modelMapper.map(grocery, GroceryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GroceryDTO getGroceryById(Long id) {
        return modelMapper.map(groceryRepository.findById(id).orElse(null), GroceryDTO.class);
    }

    @Override
    public GroceryDTO addGrocery(GroceryDTO groceryDTO) {
        Grocery grocery = modelMapper.map(groceryDTO, Grocery.class);
        return modelMapper.map(groceryRepository.save(grocery), GroceryDTO.class);
    }

    @Override
    public GroceryDTO updateGrocery(GroceryDTO groceryDTO) {
        Grocery grocery = modelMapper.map(groceryDTO, Grocery.class);
        return modelMapper.map(groceryRepository.save(grocery), GroceryDTO.class);
    }

    @Override
    public void deleteGrocery(Long id) {
        // Find and delete all associated RecipeGroceries
        List<RecipeGrocery> recipeGroceries = recipeGroceryRepository.findAllByGroceryId(id);
        recipeGroceryRepository.deleteAll(recipeGroceries);

        // Delete the Grocery itself
        groceryRepository.deleteById(id);
    }
}