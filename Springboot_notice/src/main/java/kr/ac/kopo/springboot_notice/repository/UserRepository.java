package kr.ac.kopo.springboot_notice.repository;


import kr.ac.kopo.springboot_notice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUserId(String userId); // 메서드 기반 조회

    // 기본 CRUD 메서드 제공 (save, findById 등)
}
