package br.com.estudo.message_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDTO {
    Long id;
    Long orderId;
}
