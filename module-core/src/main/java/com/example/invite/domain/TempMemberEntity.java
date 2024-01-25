package com.example.invite.domain;

import com.example.invite.dto.TempMemberDTO;
import com.example.invite.web.form.TempMemberAdminForm;
import com.example.invite.web.form.TempMemberInviteeForm;
import jakarta.persistence.*;
import lombok.*;

import java.net.http.HttpRequest;

@Data
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="temp_members")
public class TempMemberEntity {

    @Id
    @Column(name="temp_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="group_id")
    private GroupEntity groupId;


    @Column
    private String name;

    @Column
    private Integer phoneNumber;

    @Column String email;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    public static TempMemberEntity buildAdminEntity(TempMemberAdminForm adminForm){
        return TempMemberEntity.builder()
                    .name(adminForm.getName())
                    .phoneNumber(adminForm.getPhoneNumber())
                    .email(adminForm.getEmail())
                    .memberStatus(MemberStatus.TRUE)
                    .build();
    }
    public static TempMemberEntity buildInviteeEntity(TempMemberInviteeForm inviteeForm){
        return TempMemberEntity.builder()
                    .name(inviteeForm.getName())
                    .phoneNumber(inviteeForm.getPhoneNumber())
                    .email(inviteeForm.getEmail())
                    .groupId(inviteeForm.getGroupId())
                    .memberStatus(MemberStatus.FALSE)
                    .build();
    }
    public static TempMemberEntity addGroupId(TempMemberEntity adminEntity, GroupEntity groupEntity){
        adminEntity.setGroupId(groupEntity);
        return adminEntity;
    }

    public static TempMemberEntity activateMember(TempMemberEntity inviteeEntity){
        if(inviteeEntity.getMemberStatus() == MemberStatus.FALSE){
            inviteeEntity.setMemberStatus(MemberStatus.TRUE);
            return inviteeEntity;
        }else{
            throw new RuntimeException("이미 사용된 코드 입니다");
        }

    }



}
