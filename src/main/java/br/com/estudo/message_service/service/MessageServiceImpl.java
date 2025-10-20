package br.com.estudo.message_service.service;

import br.com.estudo.message_service.exception.ObjectNotFoundException;
import br.com.estudo.message_service.model.Message;
import br.com.estudo.message_service.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;

    @Override
    public Message createMessage(Message message) {
        validate(message);
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Long id) {
        getMessage(id);
        messageRepository.deleteById(id);
    }

    @Override
    public Message getMessage(Long id) {
        return messageRepository.findById(id).orElseThrow(
                ()->new ObjectNotFoundException("MessageService not found"));
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> getMessagesByUser(Long id) {
        return messageRepository.findByUserId(id);
    }

    private void validate(Message message) {
        if(message.getMessage() == null || message.getMessage().isEmpty()){
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if(message.getMessage().length()>128 || message.getMessage().length()<10){
            throw new IllegalArgumentException("Message length must be between 10 and 128");
        }
    }
}
