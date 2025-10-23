package br.com.estudo.message_service.service;

import br.com.estudo.message_service.DTO.DeliveryDTO;
import br.com.estudo.message_service.DTO.OrderDTO;
import br.com.estudo.message_service.DTO.UserDTO;
import br.com.estudo.message_service.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
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

    private static final Logger logger= LoggerFactory.getLogger(KafkaListenerService.class);

    @KafkaListener(topics = "user-created", groupId = "message_consumer")
    private void consumeUserCreated(String userJson){
        try{
            ObjectMapper mapper = new ObjectMapper();
            UserDTO user = mapper.readValue(userJson, UserDTO.class);

            logger.debug(user.toString());
;
            Long id = user.getId();
            String name = user.getName();
            String email = user.getEmail();

            String text = emailSenderService.sendUserCreatedEmail(email, name);
            Message message =new Message(id, LocalDateTime.now(), text);

            messageService.createMessage(message);

        }catch (Exception e){
            logger.error("A error happened reading a Json Value "+e.getMessage());
        }
    }

    @KafkaListener(topics = "update-delivery", groupId = "message_consumer")
    private void consumeUpdateDelivery(String deliveryJson){
        try{
            ObjectMapper mapper = new ObjectMapper();
            DeliveryDTO delivery = mapper.readValue(deliveryJson, DeliveryDTO.class);

            logger.debug(delivery.toString());

            Long orderId = delivery.getOrderId();
            String orderJson= orderClient.getOrderById(orderId);
            OrderDTO order = mapper.readValue(orderJson, OrderDTO.class);

            Long id = order.getUserId();
            String userJson= userClient.getUserById(id);
            UserDTO user = mapper.readValue(userJson, UserDTO.class);
            String email = user.getEmail();
            String name  = user.getName();

            String text = emailSenderService.sendDeliveryUpdateEmail(email, name);
            Message message =new Message(id, LocalDateTime.now(), text);

            messageService.createMessage(message);

        }catch (Exception e){
            logger.error("A error happened reading a Json Value "+e.getMessage());
        }
    }

    @KafkaListener(topics = "order-paid", groupId = "message_consumer")
    private void consume(String orderJson){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> orderMap = mapper.readValue(orderJson, Map.class);

            logger.debug(orderMap.toString());

            Number idUserNum = (Number) orderMap.get("userId");
            Long userId = idUserNum.longValue();

            String userJson=userClient.getUserById(userId);
            UserDTO user = mapper.readValue(userJson, UserDTO.class);

            String email = user.getEmail();
            String name = user.getName();

            String text = emailSenderService.sendOrderPaidEmail(email, name);
            Message message =new Message(userId, LocalDateTime.now(), text);

            messageService.createMessage(message);

        }catch (Exception e){
            logger.error("A error happened reading a Json Value "+e.getMessage());
        }
    }
}
