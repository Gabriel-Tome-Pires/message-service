package br.com.estudo.message_service.repository;

import br.com.estudo.message_service.model.Message;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(properties = "spring.kafka.listener.auto-startup=false")
@ActiveProfiles("test")
@Transactional
public class MessageRepositoryTest {
    @Autowired
    MessageRepository repository;

    @Test
    @DisplayName("Create")
    @Order(1)
    void TestCreateMessage_WithValidData_ReturnMessage(){
        Message message = new Message(1L, LocalDateTime.now(), "Test Test Test");

        Message newMessage = repository.save(message);

        Assertions.assertNotNull(message);
        Assertions.assertEquals(newMessage.getMessage(), message.getMessage());
    }

    @Test
    @DisplayName("Delete")
    @Order(2)
    void TestDeleteMessage_WithValidData(){
        Message message = new Message(1L, LocalDateTime.now(), "Test Test Test");

        Message newMessage = repository.save(message);

        repository.deleteById(newMessage.getId());
    }

    @Test
    @DisplayName("Get by id")
    @Order(3)
    void TestGetMessageById_WithValidData_ReturnMessage(){
        Message message = repository.save(
                new Message(1L, LocalDateTime.now(), "Test Test Test"));

        Message gettedMessage=repository.findById(message.getId()).orElse(null);

        Assertions.assertNotNull(gettedMessage);
        Assertions.assertEquals(message.getId(),gettedMessage.getId());
        Assertions.assertEquals(message.getMessage(), gettedMessage.getMessage());
    }

    @Test
    @DisplayName("Get all")
    @Order(4)
    void TestGetAllMessages_WithValidData_ReturnMessage(){
        Message message1 = repository.save(
                new Message(1L, LocalDateTime.now(), "Test Test Test"));
        Message message2 = repository.save(
                new Message(2L, LocalDateTime.now(), "Test Test Test"));

        List<Message> messages = repository.findAll();

        Assertions.assertNotNull(messages);
        Assertions.assertEquals(2, messages.size());
    }
}
