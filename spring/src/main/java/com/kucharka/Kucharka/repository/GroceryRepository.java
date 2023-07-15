package com.kucharka.Kucharka.repository;

import com.kucharka.Kucharka.entity.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroceryRepository extends JpaRepository<Grocery,Long> {
}
