package kr.ac.kopo.springboot_notice.security.controller;


import kr.ac.kopo.springboot_notice.dto.UserDTO;
import kr.ac.kopo.springboot_notice.security.service.JwtService;
import kr.ac.kopo.springboot_notice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/security")
@CrossOrigin(origins = "http://localhost:3000")
public class JwtController {

    // JWT service 객체 생성
    @Autowired
    private JwtService jwtService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        try {
            // Vue에서 받은 DTO 아이디를 검증 및 토큰 생성 serviceImpl로 이동, 사용자 검증 후 JwtUtil에서 토큰 생성하여 return
            String token = jwtService.authenticateAndGenerateToken(userDTO.getUserId(), userDTO.getPassword());

            // HTTP 헤더에 토큰 추가
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            // 응답 바디 데이터 생성
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("userId", userDTO.getUserId());    // 사용자 입력값이 검증됐으므로 사용.

            // HTTP 헤더와 status 200 전송
            return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        }
        // 없으면 에러 발생
        catch (Exception e) {
            // 사용자 인증 실패 status 401(인증실패) 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 토큰 검증
    @PostMapping("/validate")
    public ResponseEntity<String> validate(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        try {

            // Vue에서 받은 토큰 값을 추출 후 토큰 검증 serviceImpl로 이동, Util로 이동하여 해시값 해소 후 return, serviceImpl에서 해소된 해시값으로 사용자 인증 후 boolean 값 return
            boolean isValid = jwtService.validateTokenWithDatabase(token.replace("Bearer ", ""));

            System.out.println(isValid);
            // 해소된 해시값으로 사용자 인증을 완료 했을 경우
            if (isValid) {
                return ResponseEntity.ok("사용자 인증이 완료되었습니다.");
            }
            // 해당 토큰이 발행되지 않았을 경우 (status 204)
            else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        // 토큰 인증이 되지 않았을 경우
        catch (Exception e) {
            return ResponseEntity.status(401).body("비활성화된 사용자 인증입니다. 다시 로그인 해주세요.");
        }
    }



} // main
