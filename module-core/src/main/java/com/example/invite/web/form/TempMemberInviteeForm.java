package com.example.invite.web.form;

import com.example.invite.domain.GroupEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TempMemberInviteeForm {

    @NotBlank
    private String name;
    @NotNull
    private Integer phoneNumber;
    @NotBlank
    private String email;
    private GroupEntity groupId;

            



}
