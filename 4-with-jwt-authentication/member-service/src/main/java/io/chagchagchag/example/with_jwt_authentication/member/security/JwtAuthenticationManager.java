package io.chagchagchag.example.with_jwt_authentication.member.security;

import io.chagchagchag.example.with_jwt_authentication.jwt_wrapper.jwt.JwtDetails;
import io.chagchagchag.example.with_jwt_authentication.jwt_wrapper.jwt.JwtSupport;
import io.chagchagchag.example.with_jwt_authentication.member.application.valueobject.SecurityProperties;
import io.chagchagchag.example.with_jwt_authentication.member.security.userdetails.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
  private final JwtSupport jwtSupport;
  private final MemberDetailsService memberDetailsService;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Mono.justOrEmpty(authentication)
        .filter(auth -> auth instanceof BearerToken)
        .cast(BearerToken.class)
        .map(bearerToken -> jwtSupport.degenerateToken(jwtSupport.hmacShaKeyFor(SecurityProperties.SECRET), bearerToken.getJwt()))
        .flatMap(jwtDetails -> validateJwt(jwtDetails))
        .flatMap(jwtDetails -> findByUserId(String.valueOf(jwtDetails.id())))
        .onErrorMap(throwable -> new IllegalArgumentException("INVALID JWT"));
  }

  public Mono<JwtDetails> validateJwt(JwtDetails jwtDetails){
    if(!jwtSupport.checkIfExpired(jwtDetails.expiration())){
      return Mono.just(jwtDetails);
    }
    return Mono.error(new IllegalArgumentException("Token Invalid"));
  }

  @Transactional
  public Mono<Authentication> findByUserId(String memberId){
    return memberDetailsService.findByUsername(memberId)
        .map(memberDetails -> {
          var authentication = new UsernamePasswordAuthenticationToken(
              memberDetails.getUsername(), memberDetails.getPassword(), memberDetails.getAuthorities()
          );

          SecurityContextHolder.getContext().setAuthentication(authentication);
          return authentication;
        });
  }

  private Mono<Authentication> validate(BearerToken token){
    JwtDetails jwtDto = jwtSupport.degenerateToken(jwtSupport.hmacShaKeyFor(SecurityProperties.SECRET), token.getJwt());

    if(!jwtSupport.checkIfExpired(jwtDto.expiration())){
      return memberDetailsService
          .findByUsername(String.valueOf(jwtDto.id()))
          .map(userDetails -> new UsernamePasswordAuthenticationToken(
              userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()
          ));
    }

    throw new IllegalArgumentException("Token Invalid");
  }

}
