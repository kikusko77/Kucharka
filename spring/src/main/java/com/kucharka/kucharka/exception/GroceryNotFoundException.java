package com.kucharka.kucharka.exception;

public class GroceryNotFoundException extends RuntimeException{
    public GroceryNotFoundException(String name){super("Nebola najdena zhoda s "+name);}
}
