package com.wesell.userservice.domain.repository;

import com.wesell.userservice.domain.entity.User;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,String> {
    Page<User> findByNameContainingOrNicknameContainingOrPhoneContainingOrUuidContaining(
            String name, String nickname, String phone, String uuid, Pageable pageable
    );
    Page<User> findAll(Pageable pageable);
    boolean existsUserByNickname(String nickname);

    boolean existsUserByPhone(String phone);
}
