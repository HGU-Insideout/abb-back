package org.example.aab.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.aab.domain.User;
import org.example.aab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;



    // 카카오 인증 코드로 액세스 토큰 요청
    public String getKakaoAccessToken(String code) throws Exception {
        String tokenUri = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);
        body.add("client_secret", kakaoClientSecret);  // Client Secret 설정

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);

        // 액세스 토큰 요청
        ResponseEntity<String> response = restTemplate.exchange(
                tokenUri, HttpMethod.POST, kakaoTokenRequest, String.class);

        // JSON 파싱하여 액세스 토큰 반환
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    // 카카오 API를 통해 사용자 정보 가져오기
    public JsonNode getKakaoUserInfo(String accessToken) throws Exception {
        String userInfoUri = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);

        // 사용자 정보 요청
        ResponseEntity<String> response = restTemplate.exchange(
                userInfoUri, HttpMethod.GET, kakaoUserInfoRequest, String.class);

        // JSON 파싱하여 사용자 정보 반환
        return objectMapper.readTree(response.getBody());
    }

    public User createOrGetUser(JsonNode kakaoUserInfo) {

        Long kakaoId = kakaoUserInfo.get("id").asLong();
        System.out.println("kakaoId : " + kakaoId );
        String email = kakaoUserInfo.get("kakao_account").get("email").asText();

        // 카카오 ID로 기존 사용자가 있는지 확인
        Optional<User> userOpt = userRepository.findByKakaoId(kakaoId);
        if (userOpt.isPresent()) {
            return userOpt.get(); // 이미 존재하는 경우 해당 사용자 반환
        }

        User newUser = new User();
        newUser.setKakaoId(kakaoId);
        newUser.setEmail(email);
        return userRepository.save(newUser);  // DB에 사용자 저장
    }
}
