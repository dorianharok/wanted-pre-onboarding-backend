package com.wanted.preonboading.repository;

import com.wanted.preonboading.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
