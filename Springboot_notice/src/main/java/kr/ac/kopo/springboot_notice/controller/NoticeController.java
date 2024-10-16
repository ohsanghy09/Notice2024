package kr.ac.kopo.springboot_notice.controller;

import kr.ac.kopo.springboot_notice.dto.NoticeDTO;
import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import kr.ac.kopo.springboot_notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/messages")

public class NoticeController {

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping("find")
    public List<NoticeEntity> getAllMessage(){
        return noticeRepository.findAll();
    }

    @PostMapping("/update")
    public ResponseEntity<NoticeEntity> updateMessage(@RequestBody NoticeEntity noticeEntity) {
        Optional<NoticeEntity> optionalMessage = noticeRepository.findById(noticeEntity.getId());
        System.out.println(optionalMessage);


        if (!optionalMessage.isPresent()) {
            return ResponseEntity.notFound().build();  // 메시지를 찾을 수 없을 때
        }

        // 기존 메시지를 수정
        NoticeEntity message = optionalMessage.get();
        message.setMessage(noticeEntity.getMessage());

        // 수정된 메시지를 저장
        NoticeEntity savedMessage = noticeRepository.save(message);
        return ResponseEntity.ok(savedMessage);  // 수정된 데이터를 반환
    }

}

