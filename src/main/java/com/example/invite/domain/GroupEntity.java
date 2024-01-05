package com.example.invite.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@Table(name="group_table")
public class GroupEntity {

    @Id
    @Column(name="group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name="temp_member_id")
    private TempMemberEntity tempMemberId;

    @Column
    private String invitationLink;

    @OneToMany(mappedBy ="groupId")
    private List<TempMemberEntity> tempMembers = new ArrayList<TempMemberEntity>();

    public static GroupEntity createGroupEntity(TempMemberEntity tempMemberEntity){
        GroupEntity groupEntity = new GroupEntity();
        Link link = new Link();
        groupEntity.setTempMemberId(tempMemberEntity);
        groupEntity.setInvitationLink(link.createLink());
        return groupEntity;
    }

}
