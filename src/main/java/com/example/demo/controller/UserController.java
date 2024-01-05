package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import com.example.demo.entity.Apprenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.MessageResponse;
import com.example.demo.service.LoginResponse;
import com.example.demo.service.UserServiceImpl;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

    
	/*@GetMapping("/retrieveAllUsers")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<User> retrieveAllUsers() {

		return userRepository.findAll();
	}*/

    /*@PostMapping("/save")
    public String saveUser(@RequestBody UserDTO userDTO)
    {
        String id = userService.addUser(userDTO);
        return id;
    }*/
    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        try {
                System.out.print(loginDTO);
            LoginResponse loginResponse = userService.loginUser(loginDTO);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la connexion.");
        }
    }
    
    @PostMapping("/signupAdmin")
	public ResponseEntity<?> registerAdmin(@Validated @RequestBody UserDTO userDTO) {
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: email is already taken!"));
		}

		User user = new User(userDTO.getNom(), userDTO.getPrenom(),
				 userDTO.getDatenaiss(), userDTO.getEmail(),encoder.encode(userDTO.getPassword()),
				userDTO.getTelephone());


		Set<String> strRoles = userDTO.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role apprenantRole = roleRepository.findByName(ERole.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Erreur : Rôle 'ADMIN' introuvable."));
            roles.add(apprenantRole);
        } else {
            for (String roleName : strRoles) {
                Role role = roleRepository.findByName(ERole.valueOf(roleName))
                        .orElseThrow(() -> new RuntimeException("Erreur : Rôle introuvable."));
                roles.add(role);
            }
        }
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Admin registered successfully!"));
    }
    
    @PostMapping("/signupFormateur")
	public ResponseEntity<?> registerFormateur(@Validated @RequestBody UserDTO userDTO) {
	

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		User user =new User(userDTO.getNom(), userDTO.getPrenom(),
				  userDTO.getEmail(),userDTO.getDatenaiss(),encoder.encode(userDTO.getPassword()),
				userDTO.getTelephone());

		Set<String> strRoles = userDTO.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role apprenantRole = roleRepository.findByName(ERole.FORMATEUR)
                    .orElseThrow(() -> new RuntimeException("Erreur : Rôle 'FORMATEUR' introuvable."));
            roles.add(apprenantRole);
        } else {
            for (String roleName : strRoles) {
                Role role = roleRepository.findByName(ERole.valueOf(roleName))
                        .orElseThrow(() -> new RuntimeException("Erreur : Rôle introuvable."));
                roles.add(role);
            }
        }

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Formateur registered successfully!"));
	}

    @PostMapping("/signupApprenant")
    public ResponseEntity<?> registerEtudiant(@Validated @RequestBody UserDTO userDTO) {
        try {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }

            User user = new User();
            user.setNom(userDTO.getNom());
            user.setPrenom(userDTO.getPrenom());
            user.setEmail(userDTO.getEmail());
            user.setDatenaiss(userDTO.getDatenaiss());
            user.setPassword(encoder.encode(userDTO.getPassword()));
            user.setTelephone(userDTO.getTelephone());

            // Convert string date to Date object



            Set<String> strRoles = userDTO.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null || strRoles.isEmpty()) {
                Role apprenantRole = roleRepository.findByName(ERole.APPRENANT)
                        .orElseThrow(() -> new RuntimeException("Erreur : Rôle 'APPRENANT' introuvable."));
                roles.add(apprenantRole);
            } else {
                for (String roleName : strRoles) {
                    Role role = roleRepository.findByName(ERole.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Erreur : Rôle introuvable."));
                    roles.add(role);
                }
            }

            user.setRoles(roles);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("Apprenant registered successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering the apprenant.");
        }
    }

    @GetMapping(value = "/GetOneUser/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                // Create a DTO to hold the user data
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setNom(user.getNom());
                userDTO.setPrenom(user.getPrenom());
                userDTO.setEmail(user.getEmail());
                userDTO.setDatenaiss(user.getDatenaiss());
                userDTO.setTelephone(user.getTelephone());

                // Other necessary fields...

                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the user.");
        }

    }





    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.findUserById(userId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for ID: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the user.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {

        user.setId(id);

        // Appel de la méthode updateApprenant du service
        User updatedUser = userService.updateUser(user);

        // Retour de la réponse avec le apprenant mis à jour
        return ResponseEntity.ok(updatedUser);
    }
   
}