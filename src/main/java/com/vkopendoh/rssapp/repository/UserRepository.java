package com.vkopendoh.rssapp.repository;

import com.vkopendoh.rssapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
