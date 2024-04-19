package com.example.chatapi.applications.service;

import com.example.chatapi.model.room.Room;
import com.example.chatapi.repository.RoomRepository;
import com.example.chatapi.repository.UsersRepository;
import com.example.chatapi.web.RoomSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public Room createRoom(RoomSaveForm form) {
        Room room = roomRepository.save(Room.from(form));
       return room;
    }
}
