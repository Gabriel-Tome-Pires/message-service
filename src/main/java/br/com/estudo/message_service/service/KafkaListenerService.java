package br.com.estudo.message_service.service;

import br.com.estudo.message_service.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class KafkaListenerService {
    private EmailSenderService emailSenderService;
    private MessageService messageService;
    private final UserClient userClient;
    private final OrderClient orderClient;

    @KafkaListener(topics = "user-created", groupId = "message_consumer")
    private void consumeUserCreated(String userJson){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> messageMap = mapper.readValue(userJson, Map.class);

            System.out.println(messageMap);

            Number idNum = (Number) messageMap.get("id");
            Long id = idNum.longValue();
            String name = (String) messageMap.get("name");
            String email = (String) messageMap.get("email");

            String text = emailSenderService.sendUserCreatedEmail(email, name);
            Message message =new Message(id, LocalDateTime.now(), text);

            messageService.createMessage(message);

        }catch (Exception e){
            System.out.println("A error happened reading a Json Value "+e.getMessage());
        }
    }

    @KafkaListener(topics = "update-delivery", groupId = "message_consumer")
    private void consumeUpdateDelivery(String deliveryJson){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> deliveryMap = mapper.readValue(deliveryJson, Map.class);

            System.out.println(deliveryMap);

            Number orderIdNum = (Number) deliveryMap.get("orderId");
            Long orderId = orderIdNum.longValue();
            String orderJson= orderClient.getOrderById(orderId);
            Map<String, Object> orderMap = mapper.readValue(orderJson, Map.class);
            Number idNum = (Number) orderMap.get("userId");
            Long id = idNum.longValue();
            String userJson= userClient.getUserById(id);
            Map<String, Object> userMap = mapper.readValue(userJson, Map.class);
            String email = (String) userMap.get("email");
            String name  = (String) userMap.get("name");

            String text = emailSenderService.sendDeliveryUpdateEmail(email, name);
            Message message =new Message(id, LocalDateTime.now(), text);

            messageService.createMessage(message);

        }catch (Exception e){
            System.out.println("A error happened reading a Json Value "+e.getMessage());
        }
    }

    @KafkaListener(topics = "order-paid", groupId = "message_consumer")
    private void consume(String orderJson){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> orderMap = mapper.readValue(orderJson, Map.class);

            System.out.println(orderMap);

            Number idUserNum = (Number) orderMap.get("userId");
            Long userId = idUserNum.longValue();

            String user=userClient.getUserById(userId);
            Map<String, Object> userMap = mapper.readValue(user, Map.class);

            String email = (String) userMap.get("email");
            String name = (String) userMap.get("name");

            String text = emailSenderService.sendOrderPaidEmail(email, name);
            Message message =new Message(userId, LocalDateTime.now(), text);

            messageService.createMessage(message);

        }catch (Exception e){
            System.out.println("A error happened reading a Json Value "+e.getMessage());
        }
    }
}
