package com.example.chatapi.web;

import com.example.chatapi.model.room.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomSaveForm {

    private String name;

    private List<UsersSaveForm> users;

}
