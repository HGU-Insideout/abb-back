package org.example.aab.restcontroller;

import jakarta.servlet.http.HttpSession;
import org.example.aab.config.KakaoAPI;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
@SessionAttributes({"userId", "accessToken"})
public class LoginController {

    private final KakaoAPI kakaoApi = new KakaoAPI();

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("code") String code, Model model) {
        //로그
        System.out.println("----");
        System.out.println("호출됨");
        System.out.println("코드 : " + code);
        System.out.println("----");

        ModelAndView mav = new ModelAndView();

        // 1. 인증 코드로 액세스 토큰 요청
        String accessToken = kakaoApi.getAccessToken(code);

        // 2. 액세스 토큰으로 사용자 정보 요청
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        System.out.println("login info : " + userInfo.toString());

        // 3. 사용자 정보가 있으면 세션에 저장
        if (userInfo.get("email") != null) {
            model.addAttribute("userId", userInfo.get("email"));
            model.addAttribute("accessToken", accessToken);
        }

        // 뷰 설정 및 사용자 정보 전달
        mav.addObject("userId", userInfo.get("email"));
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        // 세션이 무효화되기 전에 액세스 토큰을 먼저 가져와서 로그아웃 처리
        String accessToken = (String) session.getAttribute("accessToken");
        if (accessToken != null) {
            kakaoApi.kakaoLogout(accessToken); // 카카오 로그아웃 API 호출
        }

        // 세션에서 액세스 토큰 및 사용자 정보 삭제
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        session.invalidate(); // 세션을 완전히 초기화

        mav.setViewName("index"); // 로그아웃 후 홈 화면으로 이동
        return mav;
    }


}
