package org.example.aab.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.aab.domain.User;
import org.example.aab.jwt.JwtUtil;
import org.example.aab.service.KakaoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @Value("${kakao.client.id}")
    private String kakaoClientId;
    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;
    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    private final KakaoService kakaoService;  // Kakao API와 통신할 서비스 클래스
    private final JwtUtil jwtUtil;            // JWT 생성 및 검증 유틸리티 클래스

    // 카카오 로그인 처리 후 리다이렉트되는 URL
    @GetMapping("/api/login")
    public ResponseEntity<String> kakaoLogin(@RequestParam("code") String code) {
        System.out.println("LoginController.kakaoLogin");
        try {
            // 1. 인증 코드로 액세스 토큰 받아오기
            String kakaoAccessToken = kakaoService.getKakaoAccessToken(code);
            System.out.println("kakaoAccessToken : " + kakaoAccessToken);
            // 2. 액세스 토큰을 사용하여 카카오 사용자 정보 가져오기
            JsonNode userInfo = kakaoService.getKakaoUserInfo(kakaoAccessToken);
            // 3. 사용자 생성 또는 조회
            User user = kakaoService.createOrGetUser(userInfo);
            // 4. 사용자 ID와 이메일을 이용해 JWT 토큰 생성
            String jwtToken = jwtUtil.createToken(user.getId(), user.getEmail(),user.isRoles());
            // 5. JWT를 HttpOnly, Secure 쿠키에 저장
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwtToken)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")  // CSRF 방지
                    .path("/")
                    .maxAge(60 * 60)  // 쿠키의 유효 시간 설정 (1시간)
                    .build();
            // 6. 응답에 쿠키 추가하고, 리다이렉트할 페이지 응답
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.LOCATION, "/")  // 로그인 후 사용자 페이지로 리다이렉트
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로그인 처리 중 오류가 발생했습니다.");
        }
    }


}
