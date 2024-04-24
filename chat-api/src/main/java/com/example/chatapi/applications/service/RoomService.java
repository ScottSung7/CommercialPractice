package com.example.chatapi.applications.service;

import com.example.chatapi.model.entity.roomAndUser.Room;
import com.example.chatapi.applications.repository.RoomRepository;
import com.example.chatapi.web.validations.exception.ChatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.chatapi.web.validations.exception.ChatErrorCode.ROOM_ALREADY_EXIST;
import static com.example.chatapi.web.validations.exception.ChatErrorCode.ROOM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    @Transactional
    public Room createRoom(String name) {
        if(roomRepository.findRoomByName(name).isPresent()){
            throw new ChatException(ROOM_ALREADY_EXIST);
        }
        return roomRepository.save(Room.from(name));}

    public Room findRoomById(String id){
        return roomRepository.findRoomById(id)
                .orElseThrow(() -> new ChatException(ROOM_NOT_FOUND));
    }

}
