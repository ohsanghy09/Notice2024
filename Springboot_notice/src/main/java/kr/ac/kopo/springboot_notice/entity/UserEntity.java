package kr.ac.kopo.springboot_notice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 아이디 값 자동 증가
    private Long id;

    private String userId; // 유저 아이디

    private String password; // 유저 비밀번호

}
