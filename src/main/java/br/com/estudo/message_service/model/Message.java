package br.com.estudo.message_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    Long userId;
    LocalDateTime createdAt;
    @NotBlank
    String message;

    public Message(Long userId, LocalDateTime createdAt, String message) {
        this.userId = userId;
        this.createdAt = createdAt;
        this.message = message;
    }
}
