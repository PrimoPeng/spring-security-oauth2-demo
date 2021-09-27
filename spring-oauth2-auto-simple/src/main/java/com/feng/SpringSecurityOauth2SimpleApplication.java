package com.feng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author primo
 * @date 2021/9/27
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
public class SpringSecurityOauth2SimpleApplication {

    public static void main(String[] args){
        SpringApplication.run(SpringSecurityOauth2SimpleApplication.class, args);
    }

}
