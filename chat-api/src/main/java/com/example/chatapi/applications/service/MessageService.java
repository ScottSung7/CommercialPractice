package com.example.chatapi.applications.service;

import com.example.chatapi.applications.repository.MessageRepository;
import com.example.chatapi.model.entity.message.Message;
import com.example.chatapi.web.validations.exception.ChatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.chatapi.web.validations.exception.ChatErrorCode.ROOM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomService roomService;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessage(String roomId) {
        if(roomService.findRoomById(roomId) == null) {
            //TODO: 여기처럼 중복되는 에러코드를 사용하지 않고 확인하는 법이 없을까?
            throw new ChatException(ROOM_NOT_FOUND);
        }
        return messageRepository.findAllByRoomId(roomId);
    }
}
