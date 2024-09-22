package org.example.aab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.aab.jwt.JwtUtil;
import org.example.aab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final JwtUtil jwtUtil;
    private final UserService userService; // UserService에서 이메일을 가져오기 위한 서비스
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/event")
    public String event() {
        return "event";
    }

    @GetMapping("/tournaments")
    public String tournaments() {
        return "tournaments";
    }


    @GetMapping("/media")
    public String media() {
        return "media";
    }

    @GetMapping("/news")
    public String news() {
        return "news";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }
    @GetMapping("/notice")
    public String notice() {
        return "notice";
    }

    @GetMapping("/login")
    public String home() {
        return "login";  // src/main/resources/templates/index.html 을 반환
    }

    @GetMapping("/check")
    public String checkUser(HttpServletRequest request, Model model) {
        // 1. Request에서 쿠키를 가져오기
        String jwtToken = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        // 2. 쿠키에서 JWT 토큰이 있는지 확인
        if (jwtToken != null && jwtUtil.validateToken(jwtToken)) {
            // 3. JWT 토큰에서 이메일 정보 추출
            String email = jwtUtil.extractEmail(jwtToken);

            // 4. JWT 토큰에서 역할 정보 추출 (roles)
            boolean roles = jwtUtil.extractRoles(jwtToken);

            // 5. Model에 이메일과 roles 추가
            model.addAttribute("email", email);
            model.addAttribute("roles", roles);

        } else {
            // JWT가 없거나 유효하지 않으면 에러 메시지 전달
            model.addAttribute("error", "로그인이 필요합니다.");
        }

        return "check"; // check.html 파일로 이동
    }
}


