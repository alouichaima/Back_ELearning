package com.example.demo.service;

import java.util.Optional;
import java.util.Set;

import com.example.demo.entity.Apprenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

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
	                String userRole = getUserRole(user.get());
					Long userId = getUserId(user.get());
					// Retrieve and set user role logic here
	                return new LoginResponse("Login Success", true, userRole, userId);
	            } else {
	                return new LoginResponse("Login Failed", false, null, null);
	            }
	        } else {
	            return new LoginResponse("Password Not Match", false, null, null);
	        }
	    }

	    // Handle the case where the user is not found
	    return new LoginResponse("Email not exists", false, null, null);
	}
	private Long getUserId(User user) {
		// Implement logic to get user role based on roles associated with the user
		// For example, you can iterate through user.getRoles() and concatenate roles
		// into a single string or choose the main role, depending on your application logic.
		// Make sure to handle null or empty roles appropriately.

		// For simplicity, let's assume user has only one role
		Long Id = user.getId();


		// Return a default role if user has no roles
		return Id;
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
	/*public User findUserById(Long id) {
		Optional<User> formOptional = userrepo.findById(id);
		if (formOptional.isEmpty()) {
			return null;
		} else {
			return formOptional.get();
		}
	}*/
	public User findUserById(Long userId) {
	    try {
	        User user = userrepo.findById(userId).orElse(null);

	        if (user == null) {
	            System.out.println("Utilisateur introuvable pour l'ID : " + userId);
	        }

	        return user;
	    } catch (Exception e) {
	        System.out.println("An error occurred while retrieving the user with ID " + userId + ": " + e.getMessage());
	        throw new RuntimeException("Error retrieving user by ID", e);
	    }
	}
	@Override
	public User updateUser(User updatedUser) {
		// Vérifier si l'id n'est pas null
		Long userId = updatedUser.getId();
		if (userId == null) {
			// Gérer ou journaliser l'erreur et renvoyer une réponse appropriée
			throw new IllegalArgumentException("L'ID de user ne peut pas être null");
		}

		// Utiliser findById pour vérifier si l'apprenant existe
		Optional<User> existingUserOptional = userrepo.findById(userId);

		// Vérifier si le user existe
		if (existingUserOptional.isEmpty()) {
			// Gérer ou journaliser l'erreur et renvoyer une réponse appropriée
			throw new EntityNotFoundException("user non trouvé avec l'ID : " + userId);
		}

		// Mise à jour de l'entité user existante avec les nouvelles données
		User existingUser = existingUserOptional.get();
		existingUser.setNom(updatedUser.getNom());
		existingUser.setPrenom(updatedUser.getPrenom());
		existingUser.setDatenaiss(updatedUser.getDatenaiss());
		existingUser.setTelephone(updatedUser.getTelephone());

		// Enregistrer les modifications dans le repository
		return userrepo.save(existingUser);
	}

	@Override
	public void saveUser(User user) {
		userrepo.save(user);
	}
}



