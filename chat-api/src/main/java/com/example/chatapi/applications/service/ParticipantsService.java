package com.example.chatapi.applications.service;

import com.example.chatapi.model.entity.roomAndUser.Participants;
import com.example.chatapi.model.entity.roomAndUser.Room;
import com.example.chatapi.model.entity.roomAndUser.Users;
import com.example.chatapi.applications.repository.ParticipantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantsService {

    private final ParticipantsRepository participantsRepository;

    public List<Participants> saveAll(Room room, List<Users> users){
        return participantsRepository.saveAll(
                users.stream()
                        .map(user -> Participants.of(room, user))
                        .collect(Collectors.toList())
                );
    }
}
