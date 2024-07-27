package io.chagchagchag.example.with_jwt_authentication.member.application.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/member-service/oauth2")
@RequestMapping("/oauth2")
public class MemberOAuth2Controller {
  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }
}
