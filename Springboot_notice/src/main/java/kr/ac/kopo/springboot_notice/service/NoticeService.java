package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.entity.NoticeEntity;

import java.util.List;

public interface NoticeService {


    // 공지사항 내용 수정
    NoticeEntity updateNotice(NoticeEntity noticeEntity);

    // 공지사항 내용 삭제
    void deleteNoticeById(Long id);

    // 공지사항 내용 추가
    NoticeEntity addNotice(NoticeEntity noticeEntity);

    // 공지사항 내용 전체 삭제
    void deleteNoticeAll();

    // 공지사항 내용 전체 삭제 (writer 값을 기준)
    void deleteRecordsByWriter(String writer);

    // 전체 데이터 개수 조회
    long getNoticeCount();

    // 시작 번호를 토대로 10개의 데이터 조회
    List<NoticeEntity> getNoticesByDate(int start);

    // 검색된 옵션으로 전체 데이터 개수 조회
    Long getNoticeCountBySearch(String option, String text);

    // 검색된 옵션으로 전체 공지사항 조회
    List<NoticeEntity> searchNotices(String option, String text, int start);


    // start 인덱스를 기반으로 공지사항을 가져옴
    List<NoticeEntity> getNoticesByPageAndDate(int start);

    // 게시판 테이블에 작성자가 있는 테이블이 있는지 확인
    boolean writerExists(String writer);

}
