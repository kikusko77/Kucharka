package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.entity.Grocery;
import com.kucharka.Kucharka.repository.GroceryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GroceryServiceImpl implements GroceryService {

    private final GroceryRepository groceryRepository;

    @Override
    public List<Grocery> getAllGroceries() {
        return groceryRepository.findAll();
    }

    @Override
    public Grocery getGroceryById(Long id) {
        return groceryRepository.findById(id).orElse(null);
    }

    @Override
    public Grocery addGrocery(Grocery grocery) {
        return groceryRepository.save(grocery);
    }

    @Override
    public Grocery updateGrocery(Grocery grocery) {
        return groceryRepository.save(grocery);
    }

    @Override
    public void deleteGrocery(Long id) {
        groceryRepository.deleteById(id);
    }
}
