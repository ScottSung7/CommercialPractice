package com.example.chatapi.model.messages;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FilePath {

    @Id
    Long id;

    String path;

    String suffix;

}
