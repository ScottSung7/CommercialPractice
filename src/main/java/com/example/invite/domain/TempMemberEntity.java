package com.example.invite.domain;

import com.example.invite.dto.TempMemberDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.net.http.HttpRequest;

@Data
@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name="temp_members")
public class TempMemberEntity {

    @Id
    @Column(name="temp_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="group_id")
    private GroupEntity groupId;


    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column String email;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    public static TempMemberEntity initialAdminEntity(TempMemberDTO adminDTO){
        TempMemberEntity adminEntity = new TempMemberEntity();

        adminEntity.setName(adminDTO.getName());
        adminEntity.setPhoneNumber(adminDTO.getPhoneNumber());
        adminEntity.setEmail(adminDTO.getEmail());
        adminEntity.setMemberStatus(MemberStatus.TRUE);
        return adminEntity;
    }
    public static TempMemberEntity addGroupId(TempMemberEntity adminEntity, GroupEntity groupEntity){
        adminEntity.setGroupId(groupEntity);
        return adminEntity;
    }
    public static TempMemberEntity toInviteeEntity(TempMemberDTO inviteeDTO, TempMemberEntity adminEntity){
        TempMemberEntity inviteeEntity = new TempMemberEntity();

        inviteeEntity.setName(inviteeDTO.getName());
        inviteeEntity.setPhoneNumber(inviteeDTO.getPhoneNumber());
        inviteeEntity.setEmail(inviteeDTO.getEmail());
        inviteeEntity.setGroupId(adminEntity.getGroupId());
        inviteeEntity.setMemberStatus(MemberStatus.FALSE);
        return inviteeEntity;
    }
    public static TempMemberEntity activateMember(TempMemberEntity inviteeEntity){
        inviteeEntity.setMemberStatus(MemberStatus.TRUE);
        return inviteeEntity;
    }



}
