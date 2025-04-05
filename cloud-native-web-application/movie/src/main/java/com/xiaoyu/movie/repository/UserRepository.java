package com.xiaoyu.movie.repository;

import com.xiaoyu.movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Find by email for authentication
}
