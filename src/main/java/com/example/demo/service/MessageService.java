package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Message;

public interface MessageService {

	 List<Message> getAllMessages();

	    Message getMessageById(Long messageId);

	    void saveMessage(Message message);
}
