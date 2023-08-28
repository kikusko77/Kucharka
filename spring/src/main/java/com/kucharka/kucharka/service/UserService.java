package com.kucharka.kucharka.service;

import com.kucharka.kucharka.DTO.UserDTO;
import com.kucharka.kucharka.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface UserService {
   List<UserDTO> getAllUsers();
   UserDTO getUserById(Long id);
   UserDTO addUser(UserDTO userDTO);
   UserDTO updateUser(UserDTO userDTO);
   void deleteUser(Long id);
   User getUser(String name);
   Collection<? extends GrantedAuthority> getAuthorities(User user);
}
