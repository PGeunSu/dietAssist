package com.rlj.dietAssist.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private Key key;

  private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24; //1시간

  public JwtTokenProvider(@Value("${spring.api.jwt}") String secret){
    byte[] keyBytes = Decoders.BASE64URL.decode(secret);
    this.key =  Keys.hmacShaKeyFor(keyBytes);
  }

  //Authentication 객체 생성
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
        .parseClaimsJws(token).getBody();

    Object rolesObject = claims.get("roles");
    String userName = claims.getSubject();

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    if (rolesObject instanceof List<?>) {
      authorities = ((List<?>) rolesObject).stream()
          .filter(String.class::isInstance) // String 타입인지 확인
          .map(role -> new SimpleGrantedAuthority((String) role))
          .toList();

    }

    User principal = new User(userName, "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  //JWT 생성
  public String generateToken(String username){
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(key)
        .compact();
  }

  // JWT 에서 사용자 이름 추출
  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key).build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  // JWT 유효성 확인
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }


}
