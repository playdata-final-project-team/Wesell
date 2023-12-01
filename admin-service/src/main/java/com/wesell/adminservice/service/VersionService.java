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

    public Map<String, String> setVersions(String jsVersion, String cssVersion, String title, String agree) {
        versions.put("jsVersion", jsVersion);
        versions.put("cssVersion", cssVersion);
        versions.put("title", title);
        versions.put("agree", agree);
        return versions;
    }
}
