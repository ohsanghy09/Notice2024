package kr.ac.kopo.springboot_notice.repository;

import jakarta.transaction.Transactional;
import kr.ac.kopo.springboot_notice.entity.BoardEntity;
import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // 전체 데이터 조회를 데이터 베이스 쿼리로 가져올 수 있는 경우
    @Query("SELECT COUNT(p) FROM BoardEntity p")
    long countNotice();

    // 날짜(String)를 기준으로 데이터를 정렬하여 가져오는 쿼리도 사용할 수 있음
    @Query(value = "SELECT * FROM BOARD_ENTITY ORDER BY TO_DATE(time, 'YYYY-MM-DD HH24:MI:SS') DESC OFFSET :start-1 ROWS FETCH NEXT 10 ROWS ONLY", nativeQuery = true)
    List<BoardEntity> findNoticesByDate(@Param("start") int start);

    // 날짜 기준으로 내림차순 정렬된 공지사항을 페이징하여 반환
    Page<BoardEntity> findAllByOrderByTimeDesc(Pageable pageable);

    // 제목에 특정 문자열이 포함된 공지사항 개수
    Long countByTitleContaining(String title);

    // 작성자에 특정 문자열이 포함된 공지사항 개수
    Long countByWriterContaining(String writer);

    // 내용에 특정 문자열이 포함된 공지사항 개수
    Long countByContentContaining(String content);

    // 제목에 특정 문자열이 포함된 공지사항을 검색하고, 페이지네이션과 정렬을 적용
    List<BoardEntity> findByTitleContaining(String title, Pageable pageable);

    // 작성자에 특정 문자열이 포함된 공지사항을 검색하고, 페이지네이션과 정렬을 적용
    List<BoardEntity> findByWriterContaining(String writer, Pageable pageable);

    // 내용에 특정 문자열이 포함된 공지사항을 검색하고, 페이지네이션과 정렬을 적용
    List<BoardEntity> findByContentContaining(String content, Pageable pageable);

    // 특정 유저 아이디(writer)로 데이터 테이블 삭제
    @Modifying
    @Transactional
    @Query("DELETE FROM BoardEntity n WHERE n.writer = :writer")
    void deleteByWriter(@Param("writer") String writer);



}
