package kr.ac.kopo.springboot_notice.repository;

import kr.ac.kopo.springboot_notice.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // 게시판 ID로 댓글 조회
    List<CommentEntity> findByNoticeIdOrderByTimeAsc(String noticeId);

}
