package com.example.chatapi.repository;

import com.example.chatapi.model.room.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
