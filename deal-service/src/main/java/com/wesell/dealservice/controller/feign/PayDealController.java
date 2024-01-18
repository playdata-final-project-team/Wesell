package com.wesell.dealservice.controller.feign;

import com.wesell.dealservice.domain.repository.read.ViewDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2")
public class PayDealController {
    private final ViewDao viewDao;
    public PayDealController(ViewDao viewDao) {
        this.viewDao = viewDao;
    }

    @GetMapping("payments/{productId}/amount")
    public ResponseEntity<Long> getPriceById(@PathVariable Long productId) {
        return ResponseEntity.ok(viewDao.searchPriceById(productId));
    }

    @GetMapping("titles/{productId}/title")
    public ResponseEntity<String> getTitleById(@PathVariable Long productId) {
        return ResponseEntity.ok(viewDao.searchTitleById(productId));
    }
}
