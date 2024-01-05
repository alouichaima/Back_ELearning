package com.example.demo.service;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.Apprenant;
import com.example.demo.entity.Formateur;
import com.example.demo.entity.User;

public interface UserService {
	
	  LoginResponse loginUser(LoginDTO loginDTO);
	public User findUserById (Long id);
	public User updateUser(User user);
	
    void saveUser(User user);



}
