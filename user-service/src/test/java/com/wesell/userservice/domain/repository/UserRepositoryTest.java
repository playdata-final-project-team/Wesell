//package com.wesell.userservice.domain.repository;
//
//import com.wesell.userservice.domain.entity.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    @Transactional  // 테스트 메서드 실행 후 롤백하여 데이터베이스 상태 유지
//    @Rollback(value = false)
//    void save() {
//        //  유저 정보 생성
//        User user = User.builder()
//                .name("Beong ho")
//                .nickname("PBH")
//                .phone("010-1234-5678")
//                .uuid("abcd1234")
//                .build();
//
//        // 유저 정보 저장
//        userRepository.save(user);
//
//        // 저장된 유저 정보 확인
//        User savedUser = userRepository.findByOneId(user.getUuid());
//        assertNotNull(savedUser);
//        assertEquals("Beong ho", savedUser.getName());
//        assertEquals("PBH", savedUser.getNickname());
//        assertEquals("010-1234-5678", savedUser.getPhone());
//        assertEquals("abcd1234", savedUser.getUuid());
//
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    void delete() {
//        User user = User.builder()
//                .name("Beong ho")
//                .nickname("PBH")
//                .phone("010-1234-5678")
//                .uuid("abcd1234")
//                .build();
//
//        // 유저 정보 저장
//        userRepository.save(user);
//
//        // 저장된 유저 정보 확인
//        User savedUser = userRepository.findByOneId(user.getUuid());
//        assertNotNull(savedUser);
//
//        // 저장된 유저 정보 삭제
//        userRepository.delete(savedUser);
//
//        // 삭제된 유저 정보 확인
//        User deletedUser = userRepository.findByOneId(savedUser.getUuid());
//        assertNull(deletedUser);
//
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    void findAll() {
//        // 새로운 유저 정보 생성
//        User user1 = User.builder()
//                .name("Beong ho")
//                .nickname("PBH")
//                .phone("010-1234-5678")
//                .uuid("abcd1234")
//                .build();
//
//        User user2 = User.builder()
//                .name("aaa")
//                .nickname("AAA")
//                .phone("010-2345-1234")
//                .uuid("addd1234")
//                .build();
//
//        // 유저 정보 저장
//        userRepository.save(user1);
//        userRepository.save(user2);
//
//        // 모든 유저 정보 조회
//        List<User> userList = userRepository.findAll();
//
//        // 조회된 유저 내용 검증
//        assertTrue(userList.contains(user1));
//        assertTrue(userList.contains(user2));
//
//    }
//}