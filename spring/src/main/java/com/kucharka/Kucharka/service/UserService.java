package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.DTO.UserDTO;
import java.util.List;

public interface UserService {
   List<UserDTO> getAllUsers();
   UserDTO getUserById(Long id);
   UserDTO addUser(UserDTO userDTO);
   UserDTO updateUser(UserDTO userDTO);
   void deleteUser(Long id);

}
