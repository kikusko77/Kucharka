package com.kucharka.kucharka.exception;

public class UserNotFoundException  extends RuntimeException{
    public UserNotFoundException(String name){super("Nebol najdeny pozivatel "+name);}
}
