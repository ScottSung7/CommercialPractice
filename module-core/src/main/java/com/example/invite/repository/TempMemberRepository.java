package com.example.invite.repository;

import com.example.invite.domain.GroupEntity;
import com.example.invite.domain.TempMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempMemberRepository extends JpaRepository<TempMemberEntity, Long> {
    List<TempMemberEntity> findByGroupId(GroupEntity myGroup);
}
