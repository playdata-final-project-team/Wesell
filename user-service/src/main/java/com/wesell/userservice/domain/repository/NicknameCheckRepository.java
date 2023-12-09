package com.wesell.userservice.domain.repository;

import com.wesell.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicknameCheckRepository extends JpaRepository<User,String> {
    boolean existsUserByNickname(String nickname);
}
