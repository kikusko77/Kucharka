package com.kucharka.kucharka.repository;

import com.kucharka.kucharka.entity.RecipeGrocery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeGroceryRepository extends JpaRepository<RecipeGrocery,Long> {
    List<RecipeGrocery> findAllByGroceryId(Long groceryId);

}
