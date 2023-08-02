package com.kucharka.Kucharka.repository;

import com.kucharka.Kucharka.entity.RecipeGrocery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeGroceryRepository extends JpaRepository<RecipeGrocery,Long> {

}
