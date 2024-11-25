package kr.ac.kopo.springboot_notice.security.service;

public interface JwtService {

    // 사용자 인증 및 토큰 생성 메서드
    String authenticateAndGenerateToken(String userId, String password);


    // 토큰 검증 및 데이터베이스 확인
    boolean validateTokenWithDatabase(String token);


}
