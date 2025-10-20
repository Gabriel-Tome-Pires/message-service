package br.com.estudo.message_service.repository;

import br.com.estudo.message_service.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserId(Long userId);
}
