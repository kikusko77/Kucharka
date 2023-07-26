package com.kucharka.Kucharka.repository;

import com.kucharka.Kucharka.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}
