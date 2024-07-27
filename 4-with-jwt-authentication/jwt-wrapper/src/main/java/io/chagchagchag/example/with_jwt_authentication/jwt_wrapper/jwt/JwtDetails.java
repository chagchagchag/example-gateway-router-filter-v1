package io.chagchagchag.example.with_jwt_authentication.jwt_wrapper.jwt;

import java.util.Date;

public record JwtDetails(
    Long id,
    String email,
    String password,
    Date expiration
) {

}
