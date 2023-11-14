package com.wesell.userservice.domain.repository;

import com.wesell.userservice.domain.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }
    public void update(User user) {
        em.merge(user);
    }

    public void delete(User user) { // 유저 정보 삭제
        em.remove(user);
    }

    public Optional<User> findByOneId(String uuid) {  // 유저 id로 유저 한명 조회

        User user = em.createQuery("select u from User u where u.uuid = :uuid", User.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
        return Optional.ofNullable(user);

    }

    public List<User> findAll() {   // 유저 정보 전체 조회
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

}
