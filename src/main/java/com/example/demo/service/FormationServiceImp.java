package com.example.demo.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ERole;
import com.example.demo.entity.Formation;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.FormationRepository;
import com.example.demo.repository.UserRepository;


@Service

public class FormationServiceImp implements FormationService{
	
		
			@Autowired
			private FormationRepository formationRepository;
			
			@Autowired
			private UserRepository userRepository;
			

		    @Autowired
		    private UserService userService; 
		    
		    @Autowired
		    private EntityManager entityManager; 


			

			public Formation ajouterFormation( String nom, String Description, Date datecreation,
					 String image) throws IOException {

				Formation formation = new Formation();

				formation.setNom(nom);
				formation.setDescription(Description);
				formation.setDatecreation(datecreation);
				formation.setImage(image);
				
				

				

				return formationRepository.save(formation);

			}


			@Override
			public List<Formation> getAllFormation() {
				return formationRepository.findAll();
			}

			@Override
			public Formation findFormationById(Long id) {
				Optional<Formation> formOptional = formationRepository.findById(id);
				return formOptional.orElse(null);
			}

			@Override
			public Formation createFormation(Formation formation) {
				return formationRepository.save(formation);
			}

			@Override
			 public Formation updateFormation(Formation formation) {
		        // Vérifier si l'id n'est pas null
		        Long formationId = formation.getId();
		        if (formationId == null) {
		            // Gérer ou journaliser l'erreur et renvoyer une réponse appropriée
		            throw new IllegalArgumentException("L'ID du formateur ne peut pas être null");
		        }

		        // Utiliser findById pour vérifier si le formateur existe
		        Optional<Formation> existingFormationOptional = formationRepository.findById(formationId);
		        
		        // Vérifier si le formateur existe
		        if (existingFormationOptional.isEmpty()) {
		            // Gérer ou journaliser l'erreur et renvoyer une réponse appropriée
		            throw new EntityNotFoundException("Formation non trouvé avec l'ID : " + formationId);
		        }

		        // Mise à jour de l'entité Formateur existante avec les nouvelles données
		        Formation existingFormation = existingFormationOptional.get();
		        existingFormation.setNom(formation.getNom());
		        existingFormation.setDescription(formation.getDescription());
		        // Autres champs à mettre à jour...

		        // Enregistrer les modifications dans le repository
		        return formationRepository.save(existingFormation);
		    }

			@Override
			public void deleteFormation(long id) {
				formationRepository.deleteById(id);
			}

			@Override
			@Transactional
			public void ajouterFormationAvecFormateur(Long userId, Formation formation) {
			    try {
			        User formateur = userService.findUserById(userId);

			        if (formateur != null) {
			            Set<Role> userRoles = formateur.getRoles();
			            System.out.println("Roles de l'utilisateur : " + userRoles);

			            if (userHasRole(formateur, ERole.FORMATEUR)) {
			                formateur.getFormations().add(formation);
			                formation.getFormateurs().add(formateur);

			                // Vous n'avez probablement pas besoin de sauvegarder la formation ici
			                // formationRepository.save(formation);

			                // Sauvegardez explicitement l'utilisateur
			                userService.saveUser(formateur);

			                entityManager.flush();
			                entityManager.clear();

			                System.out.println("Formation ajoutée avec succès.");
			            } else {
			                throw new RuntimeException("L'utilisateur n'est pas un formateur. Rôles de l'utilisateur : " + userRoles);
			            }
			        } else {
			            throw new RuntimeException("Utilisateur introuvable pour l'ID : " + userId);
			        }
			    } catch (Exception e) {
			        e.printStackTrace(); // Ajout de cette ligne pour imprimer la trace de la pile
			        throw new RuntimeException("Impossible d'ajouter la formation : " + e.getMessage());
			    }
			}


		    private boolean userHasRole(User user, ERole role) {
		        return user.getRoles().stream().anyMatch(userRole -> role.equals(userRole.getName()));
		    }



		
			

}



