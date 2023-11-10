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

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    public ResponseAdminDto saveSiteConfig(RequestAdminDto requestAdminDto) {
        SiteConfig siteConfig = new SiteConfig(convertDtoToJson(requestAdminDto));
        SiteConfig savedEntity = adminRepository.save(siteConfig);
        return modelMapper.map(savedEntity, ResponseAdminDto.class);
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
}