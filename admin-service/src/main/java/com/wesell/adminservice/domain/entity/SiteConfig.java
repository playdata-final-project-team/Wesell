package com.wesell.adminservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SiteConfig {

    @Id
    @Column(name = "configId")
    private final Long configId = 1L;

    @Lob
    @Column(name = "config")
    private String config;

    public SiteConfig(String config) {
        this.config = config;
    }
}
