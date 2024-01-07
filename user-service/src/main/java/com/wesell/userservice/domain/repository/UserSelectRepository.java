package com.wesell.userservice.domain.repository;

import com.wesell.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSelectRepository extends JpaRepository<User, String> {

    @Query("SELECT u.uuid FROM User u WHERE u.phone = :phone")
    Optional<String> findUuidByPhone(@Param("phone") String phone);

    @Query("SELECT u.nickname FROM User u WHERE u.uuid = :uuid")
    Optional<String> findNicknameByUuid(@Param("uuid") String uuid);
}
