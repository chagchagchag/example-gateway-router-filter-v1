package io.chagchagchag.example.with_jwt_authentication.member.config;

import io.chagchagchag.example.with_jwt_authentication.member.security.JwtAuthenticationManager;
import io.chagchagchag.example.with_jwt_authentication.member.security.JwtServerAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

  @Bean // WebfluxSecurity 에서는 SecurityFilterChain 이 아닌 SecurityWebFilterChain 을 return
  public SecurityWebFilterChain securityFilterChain(
      ServerHttpSecurity httpSecurity, // (참고) mvc 버전에서는 HttpSecurity 입니다.
      JwtAuthenticationManager jwtAuthenticationManager,
      JwtServerAuthenticationConverter converter // Jwt 문자열을 Authentication 객체로 바꿔준다. (구체타입 : AbstractAuthenticationToken 을 확장한 타입)
  ){
    var authenticationWebFilter = new AuthenticationWebFilter(jwtAuthenticationManager);
    authenticationWebFilter.setServerAuthenticationConverter(converter);

    return httpSecurity
        .exceptionHandling(exceptionHandlingSpec ->
            exceptionHandlingSpec.authenticationEntryPoint(
                (exchange, ex) -> Mono.fromRunnable(() -> {
                  exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                  exchange.getResponse().getHeaders().set(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
                })
            )
        )
        .csrf(csrfSpec -> csrfSpec.disable())
        .formLogin(formLoginSpec -> formLoginSpec.disable())
        .httpBasic(httpBasicSpec -> httpBasicSpec.disable())
        .authorizeExchange(authorizeExchangeSpec ->
            authorizeExchangeSpec
                .pathMatchers("/", "/welcome", "/img/**", "/api/users/signup", "/healthcheck/**")
                .permitAll()
                .pathMatchers("/swagger-ui.html", "/webjars/**")
                .permitAll()
                .pathMatchers("/healthcheck/ready")
                .permitAll()
                .pathMatchers("/api/users/login", "/api/users/signup")
                .permitAll()
                .pathMatchers("/logout", "/api/users/profile/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
        )
        .headers(headerSpec -> headerSpec.frameOptions(frameOptionsSpec -> frameOptionsSpec.disable()))
        .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }
}
