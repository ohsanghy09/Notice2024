package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import kr.ac.kopo.springboot_notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService{

    // 데이터 베이스 관련 repository 객체 생성
    @Autowired
    private NoticeRepository noticeRepository;

    // 전체 공지사항 조회
    @Override
    public List<NoticeEntity> getAllNotice() {
        return noticeRepository.findAll();  // 데이터베이스에서 전체 목록 조회 후 반환
    }

    // 공지사항 내용 수정
    @Override
    public NoticeEntity updateNotice(NoticeEntity noticeEntity) {

        // 받아온 데이터 아이디를 내부 변수로 리턴 후 데이터베이스에서 검색하여 optional entity 변수에 저장
        Optional<NoticeEntity> optionalNotice = noticeRepository.findById(noticeEntity.getId());

        // 해당 아이디에 대한 내용이 optional entity에 없으면 동작하는 조건
        if (!optionalNotice.isPresent()) {
            throw new RuntimeException("해당 공지사항을 찾을 수 없습니다.");
        }

        // 수정 로직
        NoticeEntity notice = optionalNotice.get();   // 수정하여 데이터베이스에 저장할 entity에 기존 데이터베이스에 있는 데이터를 선언 -> get
        notice.setTitle(noticeEntity.getTitle());   // 외부에서 받은 제목을 데이터베이스에 저장하기 위한 entity에 저장(변경)
        notice.setContent(noticeEntity.getContent()); // 외부에서 받은 내용을 데이터베이스에 저장하기 위한 entity에 저장(변경)
        notice.setTime(noticeEntity.getTime()); // 외부에서 받은 시간을 데이터베이스에 저장하기 위한 entity에 저장(변경)
        return noticeRepository.save(notice);  // 수정된 데이터를 저장 후 반환
    }

    // 공지사항 삭제
    @Override
    public void deleteNoticeById(Long id) { // 외부로부터 id 값 받음
        Optional<NoticeEntity> optionalNotice = noticeRepository.findById(id); // 데이터베이스에서 아이디 검색 후 optional entity에 저장

        // 해당 아이디에 대한 내용이 optional entity에 있으면 공지사항 삭제
        if (optionalNotice.isPresent()) {
            noticeRepository.deleteById(id);  // 공지사항 삭제
        } else {
            throw new RuntimeException("해당 ID의 공지사항을 찾을 수 없습니다.");
        }
    }

    // 공지사항 추가
    @Override
    public NoticeEntity addNotice(NoticeEntity noticeEntity) {
        return noticeRepository.save(noticeEntity);  // 데이터베이스에 공지사항 저장
    }

    // 공지사항 전체 삭제
    @Override
    public void deleteNoticeAll(){
        noticeRepository.deleteAll();
    }
}
