package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.entity.NoticeEntity;

import java.util.List;

public interface NoticeService {

    List<NoticeEntity> getAllMessages();
    NoticeEntity updateMessage(NoticeEntity noticeEntity);
    void deleteMessageById(Long id);
    NoticeEntity addMessage(NoticeEntity noticeEntity);
}
