package com.kucharka.kucharka.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter
public class AuthenticationRequest {
    private String name; // matches the JSON property for the username
    private String password;
    private String role;
}
