package com.example.chatapi.applications.repository;

import com.example.chatapi.model.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRoomId(String roomId);
}
