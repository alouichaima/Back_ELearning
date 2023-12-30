package com.example.demo.service;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.Formateur;
import com.example.demo.entity.User;

public interface UserService {
	
	  String addUser(UserDTO userDTO);
	  LoginResponse loginUser(LoginDTO loginDTO);
	public User findUserById (Long id);


}
