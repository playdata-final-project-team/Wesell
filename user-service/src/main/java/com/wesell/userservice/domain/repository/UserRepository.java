package com.wesell.userservice.domain.repository;

import com.wesell.userservice.domain.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {   // 유저 정보 저장
        if(user.getId() == null) {
            em.persist(user);   // 새로운 유저 정보 저장
        } else {
            em.merge(user);     // 이미 존재하는 유저 정보이므로 업데이트
        }
    }

    public void delete(User user) { // 유저 정보 삭제
        em.remove(user);
    }

    public User findByOneId(Long id) {  // 유저 id로 유저 한명 조회
            return em.createQuery("select u from User u where u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }

    public List<User> findAll() {   // 유저 정보 전체 조회
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }





}
