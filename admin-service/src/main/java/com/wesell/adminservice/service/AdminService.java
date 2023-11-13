package com.wesell.adminservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.adminservice.domain.entity.SiteConfig;
import com.wesell.adminservice.domain.dto.RequestAdminDto;
import com.wesell.adminservice.domain.dto.ResponseAdminDto;
import com.wesell.adminservice.domain.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    public ResponseAdminDto saveSiteConfig(RequestAdminDto requestAdminDto) {
        SiteConfig siteConfig = new SiteConfig(convertDtoToJson(requestAdminDto));
        SiteConfig savedEntity = adminRepository.save(siteConfig);
        return siteConfigToResponseDto(savedEntity);
    }

    private String convertDtoToJson(RequestAdminDto requestAdminDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(requestAdminDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private ResponseAdminDto siteConfigToResponseDto(SiteConfig siteConfig) {
        try {
            RequestAdminDto requestAdminDto = objectMapper.readValue(siteConfig.getConfig(), RequestAdminDto.class);
            return modelMapper.map(requestAdminDto, ResponseAdminDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseAdminDto();
        }
    }

    public ResponseAdminDto getSiteConfig() {
        Optional<SiteConfig> siteConfigOptional = adminRepository.findById(1L);
        return siteConfigOptional.map(this::siteConfigToResponseDto).orElse(new ResponseAdminDto());
    }

        public RequestAdminDto mapToRequestAdminDto(Map<String, String> versions) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(versions, RequestAdminDto.class);
    }
}