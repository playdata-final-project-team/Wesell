package com.wesell.imageserver.controller.feign;

import com.wesell.imageserver.domain.repository.ChatImageViewDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class ChatImageFeignController {

    private final ChatImageViewDao chatImageViewDao;

    @GetMapping("image-urls")
    public Map<Long,String> getUrlByProductId(List<Long> ids) {
        return chatImageViewDao.getUrlListByproductIds(ids).stream()
                .collect(Collectors.toMap(
                        obj -> (Long) obj[0],
                        obj -> (String) obj[1]
        ));
    }
}
