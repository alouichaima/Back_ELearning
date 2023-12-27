package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;

@Service
public class MessageServiceImp implements MessageService {
    @Autowired
    private MessageRepository messageRepository;


	@Override
	public List<Message> getAllMessages() {
        return messageRepository.findAll();

	}

	@Override
	public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId).orElse(null);

	}

	@Override
	public void saveMessage(Message message) {
        messageRepository.save(message);
		
	}
	
	

}
