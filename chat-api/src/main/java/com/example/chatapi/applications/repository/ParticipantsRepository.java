package com.example.chatapi.applications.repository;

import com.example.chatapi.model.entity.roomAndUser.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
}
