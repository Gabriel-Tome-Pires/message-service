package br.com.estudo.message_service.controller;

import br.com.estudo.message_service.model.Message;
import br.com.estudo.message_service.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {
    //TODO Kafka aparentemente não está recebendo todas as mensagens
    MessageService messageService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message=messageService.getMessage(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages=messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable Long userId) {
        List<Message> messages=messageService.getMessagesByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

}
