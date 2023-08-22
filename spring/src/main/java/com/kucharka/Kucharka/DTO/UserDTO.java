package com.kucharka.Kucharka.DTO;

import com.kucharka.Kucharka.roles.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Setter
@Getter
public class UserDTO {
    private Long id;
    private String name;
    private String password;
    private Role role;
    private List<RecipeDTO> recipes;
}
