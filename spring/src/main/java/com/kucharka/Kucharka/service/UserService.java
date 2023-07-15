package com.kucharka.Kucharka.service;

import com.kucharka.Kucharka.entity.Grocery;
import com.kucharka.Kucharka.entity.User;
import com.kucharka.Kucharka.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
   List<User> getAllUsers();
   User getUserById(Long id);
   User addUser(User user);
   User updateUser(User user);
   void deleteUser(Long id);

}
