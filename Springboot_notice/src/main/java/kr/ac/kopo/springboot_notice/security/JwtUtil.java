package kr.ac.kopo.springboot_notice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key SECRET_KEY = Keys.hmacShaKeyFor("a8$7hA!nfsw90kq2mv@3h1jsd%8!js3lk&2#7ksdjh@qw".getBytes()); // 서명 키
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    // JWT 생성
    public String generateToken(String userId) {
            return Jwts.builder()
                    .setSubject(userId) // 사용자 정보
                    .setIssuedAt(new Date()) // 생성 시간
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 서명
                    .compact();
    }

    // JWT 검증
    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 서명 검증
                .parseClaimsJws(token)
                .getBody();
    }


}
