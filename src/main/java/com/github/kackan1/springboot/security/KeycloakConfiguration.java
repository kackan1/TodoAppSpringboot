package com.github.kackan1.springboot.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {
    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigReslover(){
        return new KeycloakSpringBootConfigResolver();
    }
}
