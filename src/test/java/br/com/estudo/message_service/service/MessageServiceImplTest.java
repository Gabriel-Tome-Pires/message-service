package br.com.estudo.message_service.service;

import br.com.estudo.message_service.model.Message;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(properties = "spring.kafka.listener.auto-startup=false")
@ActiveProfiles("test")
@Transactional
public class MessageServiceImplTest {
    @Autowired
    MessageServiceImpl messageServiceImpl;

    @Autowired(required = false)
    private KafkaListenerEndpointRegistry registry;

    @BeforeAll
    static void disableKafkaListeners() {
        System.setProperty("spring.kafka.listener.auto-startup", "false");
    }

    @Test
    @DisplayName("Create")
    @Order(1)
    void TestCreateMessage_WithValidData_ReturnMessage(){
        Message message = new Message(1L, LocalDateTime.now(), "Test Test Test");

        Message newMessage = messageServiceImpl.createMessage(message);

        Assertions.assertNotNull(message);
        Assertions.assertEquals(newMessage.getMessage(), message.getMessage());
    }

    @Test
    @DisplayName("Delete")
    @Order(2)
    void TestDeleteMessage_WithValidData(){
        Message message = new Message(1L, LocalDateTime.now(), "Test Test Test");
        Message newMessage = messageServiceImpl.createMessage(message);

        messageServiceImpl.deleteMessage(newMessage.getId());
    }

    @Test
    @DisplayName("Get by id")
    @Order(3)
    void TestGetMessageById_WithValidData_ReturnMessage(){
        Message message = messageServiceImpl.createMessage(
                new Message(1L, LocalDateTime.now(), "Test Test Test"));

        Message gettedMessage=messageServiceImpl.getMessage(message.getId());

        Assertions.assertNotNull(gettedMessage);
        Assertions.assertEquals(message.getId(),gettedMessage.getId());
        Assertions.assertEquals(message.getMessage(), gettedMessage.getMessage());
    }

    @Test
    @DisplayName("Get all")
    @Order(4)
    void TestGetAllMessages_WithValidData_ReturnMessage(){
        Message message1 = messageServiceImpl.createMessage(
                new Message(1L, LocalDateTime.now(), "Test Test Test"));
        Message message2 = messageServiceImpl.createMessage(
                new Message(2L, LocalDateTime.now(), "Test Test Test"));

        List<Message> messages = messageServiceImpl.getAllMessages();

        Assertions.assertNotNull(messages);
        Assertions.assertEquals(2, messages.size());
    }
}
