package kr.ac.kopo.springboot_notice.controller;

import kr.ac.kopo.springboot_notice.dto.NoticeDTO;
import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import kr.ac.kopo.springboot_notice.repository.NoticeRepository;
import kr.ac.kopo.springboot_notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/notice")

public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    // 공지사항 조회
    @GetMapping("find")
    public List<NoticeEntity> getAllNotice() {
        return noticeService.getAllNotice();
    }

    // 공지사항 수정
    @PostMapping("/update")
    public ResponseEntity<NoticeEntity> updateNotice(@RequestBody NoticeEntity noticeEntity) {
        NoticeEntity updatedMessage = noticeService.updateNotice(noticeEntity);
        return ResponseEntity.ok(updatedMessage);
    }

    // 공지사항 삭제
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteNoticeById(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        noticeService.deleteNoticeById(id);
        return ResponseEntity.ok().build();
    }

    // 공지사항 등록
    @PostMapping("/add")
    public ResponseEntity<NoticeEntity> addNotice(@RequestBody NoticeEntity noticeEntity) {
        NoticeEntity savedMessage = noticeService.addNotice(noticeEntity);
        return ResponseEntity.ok(savedMessage);
    }

    // 공지사항 전체 삭제
    @DeleteMapping("/deleteAll")
    public void deleteNoticeAll() {
        noticeService.deleteNoticeAll();
    }

}

