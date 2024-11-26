package kr.ac.kopo.springboot_notice.security.securityconfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 비활성화 (테스트용, 필요에 따라 활성화)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/**", "api/security/**", "api/notice/**").permitAll() // 공개 엔드포인트
                        .anyRequest().authenticated() // 나머지는 인증 필요
                )
                .cors(Customizer.withDefaults()); // CORS 설정
        return http.build();
    }
}
