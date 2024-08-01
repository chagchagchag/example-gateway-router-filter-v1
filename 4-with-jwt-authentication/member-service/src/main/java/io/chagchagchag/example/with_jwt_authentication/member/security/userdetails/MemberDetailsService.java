package io.chagchagchag.example.with_jwt_authentication.member.security.userdetails;

import io.chagchagchag.example.with_jwt_authentication.member.dataaccess.repository.MemberR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class MemberDetailsService implements ReactiveUserDetailsService {

  private final MemberR2dbcRepository memberR2dbcRepository;

  @Override
  public Mono<UserDetails> findByUsername(String memberId) {
    return memberR2dbcRepository
        .findById(Long.parseLong(memberId))
        .map(memberEntity -> new MemberDetails(memberEntity));
  }
}
