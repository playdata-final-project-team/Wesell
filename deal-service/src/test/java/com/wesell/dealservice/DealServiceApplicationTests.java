package com.wesell.dealservice;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wesell.dealservice.domain.entity.*;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DealServiceApplicationTests {

    @Autowired
    EntityManager em;

    @Test
    void 쿼리_연결_확인() {
        Image image = new Image(1L,"testtest");
        em.persist(image);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QImage qImage = QImage.image;

        Image result = query.selectFrom(qImage)
                .fetchOne();
        Assertions.assertThat(result).isEqualTo(image);
        Assertions.assertThat(result.getId()).isEqualTo(image.getId());
    }

}
