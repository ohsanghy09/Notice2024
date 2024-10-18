package kr.ac.kopo.springboot_notice.repository;

import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    // 전체 데이터 조회
    @Query("SELECT COUNT(p) FROM NoticeEntity p")
    long countNotice();

    // 날짜(String)를 기준으로 데이터를 정렬하여 가져오는 쿼리
    @Query(value = "SELECT * FROM NOTICE_ENTITY ORDER BY TO_DATE(time, 'YYYY-MM-DD HH24:MI:SS') DESC OFFSET :start ROWS FETCH NEXT 10 ROWS ONLY", nativeQuery = true)
    List<NoticeEntity> findNoticesByDate(@Param("start") int start);
}
