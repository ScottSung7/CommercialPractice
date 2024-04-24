package com.example.chatapi.web.validations.form;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class MessageForm {

    String email;

    String type;

    String content;

}
