package com.kucharka.kucharka.exception;

public class RecipeNotFoundException extends RuntimeException{
    public RecipeNotFoundException(String name){super("Nebola najdena zhoda s "+name);}
}
