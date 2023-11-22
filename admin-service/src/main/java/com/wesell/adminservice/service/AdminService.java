package com.wesell.adminservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesell.adminservice.dto.request.AdminAuthIsForcedRequestDto;
import com.wesell.adminservice.dto.request.ChangeRoleRequestDto;
import com.wesell.adminservice.dto.response.AdminAuthIsForcedResponseDto;
import com.wesell.adminservice.dto.response.PostListResponseDto;
import com.wesell.adminservice.dto.response.UserListResponseDto;
//import com.wesell.adminservice.domain.entity.SiteConfig;
import com.wesell.adminservice.dto.request.SiteConfigRequestDto;
import com.wesell.adminservice.dto.response.SiteConfigResponseDto;
import com.wesell.adminservice.domain.enum_.Role;
//import com.wesell.adminservice.domain.repository.AdminRepository;
import com.wesell.adminservice.feignClient.AuthFeignClient;
import com.wesell.adminservice.feignClient.DealFeignClient;
import com.wesell.adminservice.feignClient.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final UserFeignClient userFeignClient;
    private final AuthFeignClient authFeignClient;
    private final DealFeignClient dealFeignClient;

    private Map<String, String> versions = new HashMap<>();
    public void setVersions(Map<String, String> versions) {
        this.versions = versions;
    }
    public Map<String, String> getVersions(){
        return this.versions;
    }

    public SiteConfigResponseDto saveSiteConfig(SiteConfigRequestDto siteConfigRequestDto) {
        return new SiteConfigResponseDto(convertDtoToJson(siteConfigRequestDto));
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

    public ResponseEntity<List<PostListResponseDto>> getPostList(String uuid) {
        return dealFeignClient.getPostList(uuid);
    }

    public AdminAuthIsForcedResponseDto updateIsForced(AdminAuthIsForcedRequestDto requestDto) {
        return authFeignClient.updateIsForced(requestDto).getBody();
    }

    public void deletePost(String uuid, Long postId) {
        dealFeignClient.deletePost(uuid, postId);
    }
}