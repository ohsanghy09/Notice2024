package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.dto.UserDTO;

public interface UserService {

    // 아이디 중복 확인
    boolean checkIfUserExists(String userId);

    // user 저장
    void saveUser(UserDTO userDTO);

    // user 삭제
    void deleteUserById(String userId);

}
