package kr.ac.kopo.springboot_notice.security.service;


import io.jsonwebtoken.Claims;
import kr.ac.kopo.springboot_notice.entity.UserEntity;
import kr.ac.kopo.springboot_notice.repository.UserRepository;
import kr.ac.kopo.springboot_notice.security.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Log4j2
public class JwtServiceImpl implements JwtService {

    // 데이터 베이스 관련 repository 객체 생성
    @Autowired
    private UserRepository userRepository;

    // JWT 관련 JwtUtil 객체 생성
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 사용자 인증 및 토큰 생성
    public String authenticateAndGenerateToken(String userId, String password) {
//        userRepository.existsByUserIdAndPassword(userId, password)
        log.info("input pw"+userId);
        Optional<UserEntity> result = userRepository.findByUserId(userId);
        log.info("db pw"+result.toString());
        UserEntity user = result.orElseThrow(() ->new IllegalArgumentException("error**"));
        log.info("db pw"+user.getPassword());

        log.info("hass"+passwordEncoder.matches(password, user.getPassword()));
        // 데이터베이스에서 사용자 확인
        if (passwordEncoder.matches(password, user.getPassword())) {
            System.out.println(jwtUtil.generateToken(userId));
            return jwtUtil.generateToken(userId);
        }
        throw new RuntimeException("Invalid username or password");
    }


    // 토큰 검증 및 데이터베이스 확인
    public boolean validateTokenWithDatabase(String token) {
        Claims claims = jwtUtil.validateToken(token); // JWT 검증
        String userId = claims.getSubject(); // 토큰에서 사용자 ID 추출
        return userRepository.existsByUserId(userId); // 데이터베이스에서 사용자 확인
    }


}
