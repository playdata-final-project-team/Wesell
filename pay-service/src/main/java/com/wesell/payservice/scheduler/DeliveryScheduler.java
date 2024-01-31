package com.wesell.payservice.scheduler;

import com.wesell.payservice.service.delivery.DeliveryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryScheduler {
    private final DeliveryService deliveryService;

    @Scheduled(cron = "${delivery.update-time}")
    public void updateToFinish(Long deliveryId) {
        deliveryService.finishDeliveryAll();
    }

    @PostConstruct
    public void init() {
        deliveryService.finishDeliveryAll();
    }
}
