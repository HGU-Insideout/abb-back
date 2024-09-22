package org.example.aab.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;



@Component
public class JwtUtil {

    @Value("${jwt.secret}")  // yml에서 secret 키 값 주입
    private String secretKey;
    @Value("${jwt.expiration}")  // yml에서 expiration 키 값 주입
    private long expirationTimeMs;

    // JWT 생성 (이메일을 subject로, userId는 클레임으로)
    public String createToken(Long userId, String email, boolean roles) {
        return Jwts.builder()
                .setSubject(email)  // 사용자 이메일을 subject로 설정
                .claim("userId", userId)  // 사용자 ID를 별도의 클레임으로 저장
                .claim("roles", roles)  // 사용자 역할 추가
                .setIssuedAt(new Date())  // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))  // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 서명 알고리즘 및 비밀키
                .compact();
    }

    // JWT에서 사용자 ID 추출 (클레임에서 userId 추출)
    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)  // yml에서 주입받은 비밀키 사용
                .parseClaimsJws(token)
                .getBody();

        return claims.get("userId", Long.class);  // 클레임에서 userId 추출
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        return extractClaim(token).getExpiration().after(new Date());
    }

    // JWT 토큰에서 클레임 추출
    private Claims extractClaim(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 이메일 추출 메서드
    public String extractEmail(String token) {
        return extractClaim(token).getSubject();  // subject에 이메일이 저장된다고 가정
    }

    public boolean extractRoles(String token) {
        Claims claims = extractClaim(token);
        return claims.get("roles", Boolean.class);  // 클레임에서 roles 값을 boolean으로 추출
    }

}
