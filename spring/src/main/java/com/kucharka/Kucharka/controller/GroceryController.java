package com.kucharka.Kucharka.controller;

import com.kucharka.Kucharka.entity.Grocery;
import com.kucharka.Kucharka.service.GroceryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class GroceryController {
    private final GroceryService groceryService;

    @GetMapping
    public ResponseEntity<List<Grocery>> getAllGroceries() {
        return new ResponseEntity<>(groceryService.getAllGroceries(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grocery> getGroceryById(@PathVariable Long id){
        return new ResponseEntity<>(groceryService.getGroceryById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Grocery> addGrocery(@RequestBody Grocery grocery){
        return new ResponseEntity<>(groceryService.addGrocery(grocery), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Grocery> updateGrocery(@RequestBody Grocery grocery){
        return new ResponseEntity<>(groceryService.updateGrocery(grocery), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGrocery(@PathVariable Long id){
        groceryService.deleteGrocery(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
