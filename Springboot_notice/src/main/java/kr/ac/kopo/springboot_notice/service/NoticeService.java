package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.entity.NoticeEntity;

import java.util.List;

public interface NoticeService {

    // 공지사항 내용 조회
    List<NoticeEntity> getAllNotice();

    // 공지사항 내용 수정
    NoticeEntity updateNotice(NoticeEntity noticeEntity);

    // 공지사항 내용 삭제
    void deleteNoticeById(Long id);

    // 공지사항 내용 추가
    NoticeEntity addNotice(NoticeEntity noticeEntity);

    // 공지사항 내용 전체 삭제
    void deleteNoticeAll();
}
