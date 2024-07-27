package io.chagchagchag.example.with_jwt_authentication.jwt_wrapper.jwt;

import io.chagchagchag.example.with_jwt_authentication.jwt_wrapper.jwt.factory.JwtDetailsFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;

public class JwtSupport {
  public JwtSupport(){}
  public String generateToken(Key key, JwtDetails jwtDetails){
    return Jwts.builder()
        .setSubject(String.format("User(id=%s)", jwtDetails.id()))
        .setExpiration(jwtDetails.expiration())
        .claim("id", jwtDetails.id())
        .claim("email", jwtDetails.email())
        .claim("password", jwtDetails.password())
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public JwtDetails degenerateToken(Key key, String token){
    JwtParser parser = Jwts.parserBuilder()
        .setSigningKey(key)
        .build();

    Jws<Claims> claimsJws = parser.parseClaimsJws(token);

    return JwtDetailsFactory.of(
        claimsJws.getBody().get("id", Long.class),
        claimsJws.getBody().get("email", String.class),
        claimsJws.getBody().get("password", String.class),
        claimsJws.getBody().getExpiration()
    );
  }

  public Boolean checkIfExpired(Date expiration){
    return expiration.after(new Date());
  }
}
