package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.dto.UserDTO;
import kr.ac.kopo.springboot_notice.entity.UserEntity;
import kr.ac.kopo.springboot_notice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public
class UserServiceImpl implements UserService{

    // repository 객체 생성
    @Autowired
    private UserRepository userRepository;

    // 아이디 중복 확인
    @Override
    public boolean checkIfUserExists(String userId) {
        return userRepository.existsByUserId(userId); // 데이터베이스에서 존재 여부 확인
    }

    // 회원 가입 유저 저장
    @Override
    public void saveUser(UserDTO userDTO){

        // DTO 데이터를 엔티티로 변환
        UserEntity userEntity = new UserEntity(); // Entity 객체 선언

        // DTO 객체 데이터 Entity에 저장
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setPassword(userDTO.getPassword());

        // JPA를 통해 데이터베이스에 저장
        userRepository.save(userEntity);


    }

}
