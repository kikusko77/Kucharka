package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.entity.Grocery;

import java.util.List;

public interface GroceryService {

    List<Grocery> getAllGroceries();
    Grocery getGroceryById(Long id);
    Grocery addGrocery(Grocery grocery);
    Grocery updateGrocery(Grocery grocery);
    void deleteGrocery(Long id);

}
