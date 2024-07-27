package io.chagchagchag.example.with_jwt_authentication.jwt_wrapper.jwt.factory;

import io.chagchagchag.example.with_jwt_authentication.jwt_wrapper.jwt.JwtDetails;
import java.util.Date;

public class JwtDetailsFactory {
  public static JwtDetails of(Long id, String email, String password, Date expiration){
    return new JwtDetails(
        id,
        email,
        password,
        expiration
    );
  }
}
