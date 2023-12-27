package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Message;
import com.example.demo.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/contacter-formateur")
    public ResponseEntity<String> showContactForm() {
        return ResponseEntity.ok("Page de formulaire de contact");
    }

    @PostMapping("/envoyer-message")
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        try {
            messageService.saveMessage(message);
            return ResponseEntity.ok("Message envoyé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'envoi du message");
        }
    }


}
