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

    @GetMapping("find")
    public List<NoticeEntity> getAllMessages() {
        return noticeService.getAllMessages();
    }

    @PostMapping("/update")
    public ResponseEntity<NoticeEntity> updateMessage(@RequestBody NoticeEntity noticeEntity) {
        NoticeEntity updatedMessage = noticeService.updateMessage(noticeEntity);
        return ResponseEntity.ok(updatedMessage);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteMessageById(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        noticeService.deleteMessageById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<NoticeEntity> addMessage(@RequestBody NoticeEntity noticeEntity) {
        NoticeEntity savedMessage = noticeService.addMessage(noticeEntity);
        return ResponseEntity.ok(savedMessage);
    }

}

