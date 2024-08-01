package io.chagchagchag.example.with_jwt_authentication.member.security.userdetails;

import io.chagchagchag.example.with_jwt_authentication.member.dataaccess.entity.MemberEntity;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberDetails implements UserDetails {
  private final MemberEntity memberEntity;

  public MemberDetails(MemberEntity memberEntity){
    this.memberEntity = memberEntity;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.stream(memberEntity.getAuthorities().split(","))
        .map(authority -> new SimpleGrantedAuthority(authority))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return memberEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return String.valueOf(memberEntity.getId());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
