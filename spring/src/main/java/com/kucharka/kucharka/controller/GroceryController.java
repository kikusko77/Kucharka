package com.kucharka.kucharka.controller;

import com.kucharka.kucharka.DTO.GroceryDTO;
import com.kucharka.kucharka.service.GroceryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groceries")
public class GroceryController {
    private final GroceryService groceryService;

    @GetMapping
    public ResponseEntity<List<GroceryDTO>> getAllGroceries() {
        return new ResponseEntity<>(groceryService.getAllGroceries(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryDTO> getGroceryById(@PathVariable Long id){
        return new ResponseEntity<>(groceryService.getGroceryById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GroceryDTO> addGrocery(@RequestBody GroceryDTO groceryDTO){
        return new ResponseEntity<>(groceryService.addGrocery(groceryDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<GroceryDTO> updateGrocery(@RequestBody GroceryDTO groceryDTO){
        return new ResponseEntity<>(groceryService.updateGrocery(groceryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGrocery(@PathVariable Long id){
        groceryService.deleteGrocery(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}