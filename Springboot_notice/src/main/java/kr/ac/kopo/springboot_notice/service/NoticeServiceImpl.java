package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import kr.ac.kopo.springboot_notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    private NoticeRepository noticeRepository;

    // 전체 공지사항 조회
    @Override
    public List<NoticeEntity> getAllMessages() {
        return noticeRepository.findAll();
    }

    // 공지사항 내용 수정
    @Override
    public NoticeEntity updateMessage(NoticeEntity noticeEntity) {
        Optional<NoticeEntity> optionalMessage = noticeRepository.findById(noticeEntity.getId());
        if (!optionalMessage.isPresent()) {
            throw new RuntimeException("해당 공지사항을 찾을 수 없습니다.");
        }

        NoticeEntity message = optionalMessage.get();
        message.setTitle(noticeEntity.getTitle());
        message.setTitle(noticeEntity.getTitle());
        message.setTime(noticeEntity.getTime());
        return noticeRepository.save(message);  // 수정된 데이터를 저장 후 반환
    }

    // 공지사항 삭제
    @Override
    public void deleteMessageById(Long id) {
        Optional<NoticeEntity> optionalMessage = noticeRepository.findById(id);
        if (optionalMessage.isPresent()) {
            noticeRepository.deleteById(id);  // 공지사항 삭제
        } else {
            throw new RuntimeException("해당 ID의 메시지를 찾을 수 없습니다.");
        }
    }

    // 공지사항 추가
    @Override
    public NoticeEntity addMessage(NoticeEntity noticeEntity) {
        return noticeRepository.save(noticeEntity);  // 공지사항 저장
    }
}
