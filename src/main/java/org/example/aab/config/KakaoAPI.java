package org.example.aab.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KakaoAPI {
    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=98d219c4217898cf3c7e2d139e23b5ca");
            sb.append("&redirect_uri=http://localhost:8080/api/login");
            sb.append("&code="+code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
//            System.out.println("response code = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";
            while((line = br.readLine())!=null) {
                result += line;
            }
            System.out.println("response body="+result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }


    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body = " + result.toString());

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            // `kakao_account` 객체 존재 여부 확인
            if (element.getAsJsonObject().has("kakao_account")) {
                JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
                if (kakaoAccount.has("email")) {
                    String email = kakaoAccount.get("email").getAsString();
                    userInfo.put("email", email);
                } else {
                    System.out.println("Email not available");
                }
            } else {
                System.out.println("Kakao account info not available");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public void kakaoLogout(String accessToken) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            // Response code 확인
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            // 응답 코드가 200이 아닐 경우 에러 처리
            if (responseCode != 200) {
                BufferedReader brError = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResult = new StringBuilder();
                String errorLine;
                while ((errorLine = brError.readLine()) != null) {
                    errorResult.append(errorLine);
                }
                System.out.println("Error response: " + errorResult.toString());
                return;
            }

            // 성공적으로 로그아웃이 된 경우 결과 출력
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("Logout success response: " + result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
