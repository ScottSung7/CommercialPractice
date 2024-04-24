package com.example.chatapi.applications.repository;

import com.example.chatapi.model.entity.roomAndUser.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findUsersByEmailAndType(String email, String type);
}
