package com.example.chatapi.applications.application;

import com.example.chatapi.applications.service.RoomService;
import com.example.chatapi.applications.service.ParticipantsService;
import com.example.chatapi.applications.service.UsersService;
import com.example.chatapi.model.entity.roomAndUser.Room;
import com.example.chatapi.model.entity.roomAndUser.Participants;
import com.example.chatapi.model.entity.roomAndUser.Users;
import com.example.chatapi.web.validations.form.room.RoomAddUserForm;
import com.example.chatapi.web.validations.form.room.RoomSaveForm;
import com.example.chatapi.web.validations.form.room.UsersSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomApplication {
    //TODO: UPDATE, DELETE
    //User의 채팅 접속 기록 같은걸 Follow하려면 Users가 있는 경우가 좀 복잡해도 더 좋을것 같기는 함.
    //다만 addNewUsers()를 항상 사용하는것은 복잡도만 증가해 회원가입때 Users도 추가가 된다면 더 좋을 것 같다.
    //초대 받은적 없을 때는. 채팅 접속시 내 정보를 가져오는 API로 일단 해결을 하자.
    private final RoomService roomService;
    private final UsersService usersService;
    private final ParticipantsService participantsService;

    public Room createRoom(RoomSaveForm form) {
        addNewUsers(form);
        List<Users> usersToAdd = getUsersToAdd(form);
        Room room = roomService.createRoom(form.getName());
        return registerParticipants(room, usersToAdd);
    }
    public Room addUsers(RoomAddUserForm form) {
        addNewUsers(form);
        List<Users> usersToAdd = getUsersToAdd(form);
        Room room = roomService.findRoomById(form.getRoomId());
        return registerParticipants(room, usersToAdd);
    }

    private List<Users> addNewUsers(RoomSaveForm form){
        List<UsersSaveForm> newUsersSaveForms = usersService.findNewUsers(form);
        return newUsersSaveForms.stream().map(usersService::addUsers).collect(Collectors.toList());
    }
    private List<Users> getUsersToAdd(RoomSaveForm form){
        return form.getUsersSaveForms().stream()
                .map(userSaveForm -> usersService.findUsersByEmailAndType(userSaveForm.getEmail(), userSaveForm.getType()))
                .collect(Collectors.toList());
    }
    private Room registerParticipants(Room room, List<Users> usersToAdd){
        List<Participants> participants = participantsService.saveAll(room, usersToAdd);
        participants.stream().forEach(par -> par.getUser().addRoom(par));
        room.addParticipants(participants);
        return room;
    }


    //find
    public Room findRoomById(String id){
        return roomService.findRoomById(id);
    }


}
