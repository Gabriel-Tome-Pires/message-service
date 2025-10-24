package br.com.estudo.message_service.service;

import br.com.estudo.message_service.exception.ObjectNotFoundException;
import br.com.estudo.message_service.model.Message;
import br.com.estudo.message_service.repository.MessageRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {
    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    MessageServiceImpl messageServiceImpl;

    @BeforeAll
    static void disableKafkaListeners() {
        System.setProperty("spring.kafka.listener.auto-startup", "false");
    }

    @Test
    @DisplayName("Create with valid data")
    void TestCreateMessage_WithValidData_ReturnMessage(){
        Message input = new Message(1L, LocalDateTime.now(), "Test Test Test");
        Message output = new Message(1L, input.getCreatedAt(), "Test Test Test");
        output.setId(10L);

        Mockito.when(messageRepository.save(input)).thenReturn(output);
        Message result = messageServiceImpl.createMessage(input);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(output.getId(), result.getId());
        Assertions.assertEquals(output.getMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Create with empty message")
    void TestCreateMessage_WithEmptyMessage_ReturnError(){
        Message input = new Message(1L, LocalDateTime.now(), "");

        Assertions.assertThrows(IllegalArgumentException.class, () -> messageServiceImpl.createMessage(input));
    }

    @Test
    @DisplayName("Create with null message")
    void TestCreateMessage_WithNullMessage_ReturnError(){
        Message input = new Message(1L, LocalDateTime.now(), null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> messageServiceImpl.createMessage(input));
    }

    @Test
    @DisplayName("Create with short message")
    void TestCreateMessage_WithShortMessage_ReturnError(){
        Message input = new Message(1L, LocalDateTime.now(), "dad");

        Assertions.assertThrows(IllegalArgumentException.class, () -> messageServiceImpl.createMessage(input));
    }

    @Test
    @DisplayName("Create with long message")
    void TestCreateMessage_WithLongMessage_ReturnError(){
        Message input = new Message(1L, LocalDateTime.now(), "dadlljnnmomkmnlkmlkmlkmlkklmlk" +
                "ononmomokmlkmlkmokimokmoknmljknmonmojnihbulhbl64fv97w4hjblihbiçnçiunçiunçinçkjnkj kj kjk khkjhnwvwvc" +
                "fwefwfewfwfwfewefn whkjcvnkwjhvn kejfnvwref8164fw6e8f41w6ef186wf168ewf46we816f8w116fw75");

        Assertions.assertThrows(IllegalArgumentException.class, () -> messageServiceImpl.createMessage(input));
    }

    @Test
    @DisplayName("Delete")
    void TestDeleteMessage_WithValidData(){
        Message output = new Message(1L, LocalDateTime.now(), "Test Test Test");
        output.setId(10L);

        Mockito.when(messageRepository.findById(10L)).thenReturn(Optional.of(output));

        messageServiceImpl.deleteMessage(output.getId());
    }

    @Test
    @DisplayName("Get by id with existing id")
    void TestGetMessageById_WithValidData_ReturnMessage(){
        Message output =  new Message(1L, LocalDateTime.now(), "Test Test Test");
        output.setId(10L);

        Mockito.when(messageRepository.findById(output.getId())).thenReturn(Optional.of(output));
        Message result=messageServiceImpl.getMessage(output.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(output.getId(),result.getId());
        Assertions.assertEquals(output.getMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Get by id with a not existing id")
    void TestGetMessageById_WithInvalidData_ReturnError(){
        Message output =  new Message(1L, LocalDateTime.now(), "Test Test Test");
        output.setId(10L);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> messageServiceImpl.getMessage(output.getId()));
    }

    @Test
    @DisplayName("Get by user id")
    void TestGetMessageByUserId_WithValidData_ReturnMessage(){
        Message output =  new Message(1L, LocalDateTime.now(), "Test Test Test");
        output.setId(10L);
        List<Message> messages = Arrays.asList(output);

        Mockito.when(messageRepository.findByUserId(output.getUserId())).thenReturn(messages);
        List<Message> result=messageServiceImpl.getMessagesByUser(output.getUserId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(messages.size(), result.size());
        Assertions.assertTrue(result.containsAll(messages));
    }

    @Test
    @DisplayName("Get all")
    void TestGetAllMessages_WithValidData_ReturnMessage(){
        Message message1 =  new Message(1L, LocalDateTime.now(), "Test Test Test");
        Message message2 =  new Message(1L, LocalDateTime.now(), "Test Test Test");
        List<Message> messages = Arrays.asList(message1,message2);

        Mockito.when(messageRepository.findAll()).thenReturn(messages);
        List<Message> messagesResult = messageServiceImpl.getAllMessages();

        Assertions.assertNotNull(messagesResult);
        Assertions.assertEquals(messages.size(), messagesResult.size());
        Assertions.assertTrue(messagesResult.containsAll(messages));
    }
}
