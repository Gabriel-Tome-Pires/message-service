package br.com.estudo.message_service.service;

import br.com.estudo.message_service.model.Message;

import java.util.List;

public interface MessageService {
    Message createMessage(Message message);
    void deleteMessage(Long id);
    Message getMessage(Long id);
    List<Message> getAllMessages();
    List<Message> getMessagesByUser(Long id);
}
