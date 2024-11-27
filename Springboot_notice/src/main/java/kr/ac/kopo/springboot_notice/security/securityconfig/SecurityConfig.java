package kr.ac.kopo.springboot_notice.security.securityconfig;


import kr.ac.kopo.springboot_notice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성합니다.
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //패스워드 암호화하는 클래스
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 비활성화 (테스트용, 필요에 따라 활성화)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/**", "api/security/**", "api/notice/**", "api/comment/**", "api/board/**").permitAll() // 공개 엔드포인트
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .cors(Customizer.withDefaults()); // CORS 설정


        return http.build();
    }
}
