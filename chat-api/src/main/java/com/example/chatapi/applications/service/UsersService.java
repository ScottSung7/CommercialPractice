package com.example.chatapi.applications.service;

import com.example.chatapi.model.entity.roomAndUser.Users;
import com.example.chatapi.applications.repository.UsersRepository;
import com.example.chatapi.web.validations.form.room.RoomSaveForm;
import com.example.chatapi.web.validations.form.room.UsersSaveForm;
import com.example.chatapi.web.validations.exception.ChatException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.chatapi.web.validations.exception.ChatErrorCode.USER_ALREADY_EXIST;
import static com.example.chatapi.web.validations.exception.ChatErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional
    public Users addUsers(UsersSaveForm form) {
        if(usersRepository.findUsersByEmailAndType(form.getEmail(), form.getType()).isPresent()){
            throw new ChatException(USER_ALREADY_EXIST);
        }
        return usersRepository.save(Users.from(form));
    }

    public List<UsersSaveForm> findNewUsers(RoomSaveForm form) {
        return form.getUsersSaveForms().stream()
                .filter(userSaveForm -> usersRepository.findUsersByEmailAndType(userSaveForm.getEmail(), userSaveForm.getType()).isEmpty())
                .collect(Collectors.toList());
    }

    public Users findUsersByEmailAndType(String email, String type){
        return usersRepository.findUsersByEmailAndType(email, type)
                .orElseThrow(() -> new ChatException(USER_NOT_FOUND));
    }



}
