package io.chagchagchag.example.with_jwt_authentication.member.config;

import io.chagchagchag.example.with_jwt_authentication.jwt_wrapper.jwt.JwtSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

  @Bean
  public JwtSupport jwtSupport(){
    return new JwtSupport();
  }

}
