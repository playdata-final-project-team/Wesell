package com.wesell.userservice.domain.repository;

import com.wesell.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminUserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContaining(String name);
    List<User> findByNicknameContaining(String nickname);
    List<User> findByPhoneContaining(String phone);
    List<User> findByUuidContaining(String uuid);
}
