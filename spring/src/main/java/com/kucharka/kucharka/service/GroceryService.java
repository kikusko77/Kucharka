package com.kucharka.kucharka.service;

import com.kucharka.kucharka.DTO.GroceryDTO;
import java.util.List;

public interface GroceryService {

    List<GroceryDTO> getAllGroceries();
    GroceryDTO getGroceryById(Long id);
    GroceryDTO addGrocery(GroceryDTO groceryDTO);
    GroceryDTO updateGrocery(GroceryDTO groceryDTO);
    void deleteGrocery(Long id);

}
