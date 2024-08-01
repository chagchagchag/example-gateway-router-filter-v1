package io.chagchagchag.example.with_jwt_authentication.member.dataaccess.entity.factory;

import io.chagchagchag.example.with_jwt_authentication.member.dataaccess.entity.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberEntityFactory {
  public MemberEntity ofNewMember(
      String email,
      String password,
      String authorities
  ){
    return MemberEntity.creationBuilder()
        .email(email)
        .password(password)
        .authorities(authorities)
        .build();
  }
}
