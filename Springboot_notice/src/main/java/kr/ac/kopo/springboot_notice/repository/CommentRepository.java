package kr.ac.kopo.springboot_notice.repository;

import kr.ac.kopo.springboot_notice.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
