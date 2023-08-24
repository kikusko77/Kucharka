package com.kucharka.Kucharka.repository;

import com.kucharka.Kucharka.entity.Recipe;
import com.kucharka.Kucharka.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    List<Recipe> findAllByUser(User user);

}
