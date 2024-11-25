package kr.ac.kopo.springboot_notice.security.service;


import io.jsonwebtoken.Claims;
import kr.ac.kopo.springboot_notice.repository.UserRepository;
import kr.ac.kopo.springboot_notice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    // 데이터 베이스 관련 repository 객체 생성
    @Autowired
    private UserRepository userRepository;

    // JWT 관련 JwtUtil 객체 생성
    @Autowired
    JwtUtil jwtUtil;

    // 사용자 인증 및 토큰 생성
    public String authenticateAndGenerateToken(String userId, String password) {

        // 데이터베이스에서 사용자 확인
        if (userRepository.existsByUserIdAndPassword(userId, password)) {
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
