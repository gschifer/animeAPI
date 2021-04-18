package com.example.animeapi.repository;

import com.example.animeapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String username);
}
