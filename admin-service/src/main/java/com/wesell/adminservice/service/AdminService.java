package com.wesell.adminservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.adminservice.domain.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.domain.dto.response.UserListResponseDto;
import com.wesell.adminservice.domain.entity.SiteConfig;
import com.wesell.adminservice.domain.dto.request.SiteConfigRequestDto;
import com.wesell.adminservice.domain.dto.response.SiteConfigResponseDto;
import com.wesell.adminservice.domain.enum_.Role;
import com.wesell.adminservice.domain.repository.AdminRepository;
import com.wesell.adminservice.feignClient.AuthFeignClient;
import com.wesell.adminservice.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final UserFeignClient userFeignClient;
    private final AuthFeignClient authFeignClient;

    public SiteConfigResponseDto saveSiteConfig(SiteConfigRequestDto siteConfigRequestDto) {
        SiteConfig siteConfig = new SiteConfig(convertDtoToJson(siteConfigRequestDto));
        SiteConfig savedEntity = adminRepository.save(siteConfig);
        return siteConfigToResponseDto(savedEntity);
    }

    private String convertDtoToJson(SiteConfigRequestDto siteConfigRequestDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(siteConfigRequestDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private SiteConfigResponseDto siteConfigToResponseDto(SiteConfig siteConfig) {
        try {
            SiteConfigRequestDto siteConfigRequestDto = objectMapper.readValue(siteConfig.getConfig(), SiteConfigRequestDto.class);
            return modelMapper.map(siteConfigRequestDto, SiteConfigResponseDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new SiteConfigResponseDto();
        }
    }

    public SiteConfigResponseDto getSiteConfig() {
        Optional<SiteConfig> siteConfigOptional = adminRepository.findById(1L);
        return siteConfigOptional.map(this::siteConfigToResponseDto).orElse(new SiteConfigResponseDto());
    }

    public ResponseEntity<List<UserListResponseDto>> getUserList(){
        return userFeignClient.getUserList();
    }

        public SiteConfigRequestDto mapToRequestAdminDto(Map<String, String> versions) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(versions, SiteConfigRequestDto.class);
    }

    public void changeUserRole(String uuid, Role role) {
        ChangeRoleRequestDto requestDto = new ChangeRoleRequestDto();
        requestDto.setRole(role);

        ResponseEntity<String> response = authFeignClient.changeUserRole(uuid, requestDto);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("User role changed successfully");
        } else {
            System.out.println("Failed to change user role: " + response.getBody());
        }
    }
}