package com.kucharka.kucharka.repository;

import com.kucharka.kucharka.entity.Recipe;
import com.kucharka.kucharka.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    List<Recipe> findAllByUser(User user);

}
