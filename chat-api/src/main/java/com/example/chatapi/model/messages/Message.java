package com.example.chatapi.model.messages;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class Message {

    @Id
    @GeneratedValue(generator = "uuid")
    public Long id;

    String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    FilePath filePath;

}
