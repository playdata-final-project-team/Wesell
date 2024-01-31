package com.wesell.payservice.service.pay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wesell.payservice.domain.dto.request.DeliveryUpdateRequestDto;
import com.wesell.payservice.domain.dto.request.PayRequestDto;
import com.wesell.payservice.domain.entity.Delivery;
import com.wesell.payservice.domain.entity.Pay;
import com.wesell.payservice.domain.repository.PayRepository;
import com.wesell.payservice.enumerate.PayType;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PayServiceImplTest {
    @Autowired
    private PayServiceImpl service;
    @Autowired
    private PayRepository repository;

    @BeforeEach
    public void insert() {
        Pay pay1 = new Pay(1L, 1L, "내가 구매자uuid", 1L, "ordernum", PayType.ASSURED, 3000L, LocalDateTime.now(),false);
        repository.saveAndFlush(pay1);
    }

    @AfterEach
    public void delete() {
        repository.deleteAll();
    }

    @Test
    void 동시_구매_테스트() throws InterruptedException {
        int PEOPLE = 10; //10명이 동시 요청
        CountDownLatch countDownLatch = new CountDownLatch(PEOPLE);
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        for (int i =0 ;i<100; i++ ){ //반복문으로 요청 100개 넣기
            executorService.submit(() -> { //개별 스레드가 호출할 요청
                try{
                    service.createPay(new PayRequestDto( 1L, "구매하고싶어요", 1L, 0));
                } finally {
                    countDownLatch.countDown(); //요청 들어간 스레드는 대기
                }
            });
        }
        countDownLatch.await();

        assertEquals(1, repository.findAllById(Collections.singleton(1L)).size());
    }
}
