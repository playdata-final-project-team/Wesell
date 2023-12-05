package com.wesell.userservice.domain.repository;

import com.wesell.userservice.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<User, Long> {
    Page<User> findByNameContainingOrNicknameContainingOrPhoneContainingOrUuidContaining(
            String name, String nickname, String phone, String uuid, Pageable pageable
    );
    Page<User> findAll(Pageable pageable);
}
