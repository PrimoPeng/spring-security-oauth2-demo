package com.feng.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final @NonNull
    AuthenticationManager authenticationManager;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        builder.withClient("oauth2")
                .secret("$2a$10$lEUfBcsTgHBPtEGNmxMUSuQzhSiK69HsbonZ9vy1mNj8EGWV1zvpO")
                .resourceIds("oauth2")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .scopes("all")
                .accessTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofHours(1).getSeconds()))
                .resourceIds("http://example.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain);
                //.accessTokenConverter(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称加密
        //converter.setSigningKey("oauth2");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "123456".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2"));
        return converter;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = new HashMap<>(1);
            additionalInfo.put("organization", authentication.getName());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }



}