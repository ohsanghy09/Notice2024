package kr.ac.kopo.springboot_notice.repository;

import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
}
