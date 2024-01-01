package com.example.demo.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	/*@Override
	public String addUser(UserDTO userDTO) {
		   User user = new User(
				   userDTO.getId(),
				   userDTO.getNom(),
				   userDTO.getPrenom(),
				   userDTO.getDatenaiss(),
				   userDTO.getEmail(),
	                this.passwordEncoder.encode(userDTO.getPassword()),
	                userDTO.getTelephone(),
	                userDTO.getPhoto()
 
	        );
		   userrepo.save(user);
	        return user.getNom();
	}

	UserDTO userDTO;*/
	@Override
	public LoginResponse loginUser(LoginDTO loginDTO) {
	    String msg = "";
	    User user1 = userrepo.findByEmail(loginDTO.getEmail());
	    if (user1 != null) {
	        String password = loginDTO.getPassword();
	        String encodedPassword = user1.getPassword();
	        Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);

			if (isPwdRight) {
	            Optional<User> user = userrepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
	            if (user.isPresent()) {
	                String userRole = getUserRole(user.get()); // Retrieve and set user role logic here
	                return new LoginResponse("Login Success", true, userRole);
	            } else {
	                return new LoginResponse("Login Failed", false, null);
	            }
	        } else {
	            return new LoginResponse("Password Not Match", false, null);
	        }
	    }

	    // Handle the case where the user is not found
	    return new LoginResponse("Email not exists", false, null);
	}

	private String getUserRole(User user) {
	    // Implement logic to get user role based on roles associated with the user
	    // For example, you can iterate through user.getRoles() and concatenate roles
	    // into a single string or choose the main role, depending on your application logic.
	    // Make sure to handle null or empty roles appropriately.
	    
	    // For simplicity, let's assume user has only one role
	    Set<Role> roles = user.getRoles();
	    if (roles != null && !roles.isEmpty()) {
	        return roles.iterator().next().getName().toString();
	    }

	    // Return a default role if user has no roles
	    return "DEFAULT_ROLE";
	}

 
	@Override
	public String addUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
