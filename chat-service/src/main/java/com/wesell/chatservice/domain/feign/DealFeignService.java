package com.wesell.chatservice.domain.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="DEAL-SERVICE")
public interface DealFeignService {


}
