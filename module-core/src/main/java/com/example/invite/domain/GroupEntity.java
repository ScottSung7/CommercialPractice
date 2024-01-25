package com.example.invite.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@Table(name="group_table")
public class GroupEntity {

    @Id
    @Column(name="group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = LAZY)
    @JoinColumn(name="admin_id", referencedColumnName = "temp_member_id")
    private TempMemberEntity adminId;


    @Column
    private String invitationLink;

    @OneToMany(mappedBy ="groupId")
    private List<TempMemberEntity> tempMembers = new ArrayList<TempMemberEntity>();

    @Builder
    public GroupEntity(TempMemberEntity adminId, String invitationLink) {
        this.adminId = adminId;
        this.invitationLink = invitationLink;
    }

    public static GroupEntity createGroupEntity(TempMemberEntity tempMemberEntity){
        Link link = new Link();

        return GroupEntity.builder()
                .adminId(tempMemberEntity)
                .invitationLink(link.createLink())
                .build();
    }

}
