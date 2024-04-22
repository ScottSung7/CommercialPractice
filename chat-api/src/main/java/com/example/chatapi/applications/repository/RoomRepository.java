package com.example.chatapi.applications.repository;

import com.example.chatapi.model.entity.roomAndUser.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findRoomById(String id);

    Optional<Room> findRoomByName(String name);
}
