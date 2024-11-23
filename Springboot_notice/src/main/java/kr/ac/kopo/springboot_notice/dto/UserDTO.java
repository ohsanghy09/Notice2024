package kr.ac.kopo.springboot_notice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String userId;  // 유저 아이디
    private String password;    // 유저 비밀번호

}
