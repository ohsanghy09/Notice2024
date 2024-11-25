package kr.ac.kopo.springboot_notice.repository;


import kr.ac.kopo.springboot_notice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 데이터베이스에 해당 아이디 여부 확인
    boolean existsByUserId(String userId); // 메서드 기반 조회

    // 기본 CRUD 메서드 제공 (save, findById 등)

    // userId와 password로 존재 여부 확인
    boolean existsByUserIdAndPassword(String userId, String password);

    void deleteByUserId(String userId); // userId로 데이터 삭제
}
