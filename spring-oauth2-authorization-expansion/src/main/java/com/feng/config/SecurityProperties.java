package com.feng.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("application.security.oauth")
public class SecurityProperties {

    /**
     * 登录请求的路径，默认值 /authorization/form
     */
    private String loginProcessingUrl = "/authorization/form";

}
