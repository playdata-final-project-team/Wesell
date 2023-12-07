package com.wesell.adminservice.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Service
public class VersionService {
    private Map<String, String> versions = new HashMap<>();

    // 생성자를 통해 초기값 설정
    public VersionService(String jsVersion, String cssVersion, String title, String agree) {
        versions.put("jsVersion", jsVersion);
        versions.put("cssVersion", cssVersion);
        versions.put("title", title);
        versions.put("agree", agree);
    }

    public VersionService() {
        // 기본값을 원하는 값으로 설정
        this("1.0", "1.0", "Default Title", "개인정보 제공에 동의하십니까?");
    }

    public Map<String, String> setVersions(String jsVersion, String cssVersion, String title, String agree) {
        versions.put("jsVersion", jsVersion);
        versions.put("cssVersion", cssVersion);
        versions.put("title", title);
        versions.put("agree", agree);
        return versions;
    }
}
