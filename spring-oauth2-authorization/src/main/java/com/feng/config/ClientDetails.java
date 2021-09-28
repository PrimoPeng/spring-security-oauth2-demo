package com.feng.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("application.security.oauth")
public class ClientDetails {
    private List<BaseClientDetails> client;
}