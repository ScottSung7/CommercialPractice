package com.example.chatapi.repository;

import com.example.chatapi.model.room.Room;
import com.example.chatapi.model.room.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
