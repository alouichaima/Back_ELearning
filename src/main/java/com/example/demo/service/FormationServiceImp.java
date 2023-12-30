package com.example.demo.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Formation;
import com.example.demo.repository.FormationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.MessageResponse;


@Service

public class FormationServiceImp implements FormationService{
	
		
			@Autowired
			private FormationRepository formationRepository;
			
			@Autowired
			private UserRepository userRepository;
			

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
		
}



