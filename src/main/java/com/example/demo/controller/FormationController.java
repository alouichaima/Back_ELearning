package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Formation;
import com.example.demo.repository.FormationRepository;
import com.example.demo.response.MessageResponse;
import com.example.demo.service.FormationService;
import com.example.demo.service.FormationServiceImp;

@RequestMapping("/formation")
@RestController
public class FormationController {
	
	@Autowired
	private FormationService formationService;
	
	@Autowired
	private FormationRepository formationRepository;
	
	   @Autowired
	    private FormationServiceImp formationServiceImp;
	
 


	@PostMapping("/AjouterFormation")
	public ResponseEntity<MessageResponse> AjouterFormation(
			@RequestParam("nom") String nom, @RequestParam("Description") String Description,
			@RequestParam("datecreation") @DateTimeFormat(pattern = "yyyy-MM-dd") Date datecreation, @RequestParam("image") String image) {

		String message = "";

		try {

			formationServiceImp.ajouterFormation(nom, Description, datecreation, image);
			message = "Formation Ajouter Avec success: ";
			return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
		}

	}
	
	
	
	@GetMapping(path="/all")
	public List<Formation> getAllFormation() {
		return formationService.getAllFormation();
		
	}
	
	@PostMapping(path="/add")
	public Formation createFormation(@RequestBody Formation formation) {
		return formationService.createFormation(formation);
	}

	
	 @PutMapping("/{id}")
	    public ResponseEntity<Formation> updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
	        formation.setId(id);

	        Formation updatedFormation = formationService.updateFormation(formation);

	        return ResponseEntity.ok(updatedFormation);
	    }
	    
	
	@GetMapping("/{id}")
	public ResponseEntity<Formation> findFormationById (@PathVariable Long id) {
		Formation formation = formationService.findFormationById(id);
		if (formation==null) {
			return new ResponseEntity<Formation>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Formation>(formation, HttpStatus.OK);
		}
	}
	
    @PostMapping("/ajouterFormation/{userId}")
    public ResponseEntity<MessageResponse> ajouterFormation(@PathVariable Long userId, @RequestBody Formation formation) {
        String message = "";

        try {
            formationService.ajouterFormationAvecFormateur(userId, formation);
            message = "Formation ajoutée avec succès.";
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
        } catch (Exception e) {
            message = "Impossible d'ajouter la formation : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
        }
    }

}
