package com.feng.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final @NonNull
    AuthenticationManager authenticationManager;

    private final @NonNull
    RedisConnectionFactory redisConnectionFactory;


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
        endpoints.authenticationManager(this.authenticationManager)
                .tokenStore(redisTokenStore());
    }

    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}