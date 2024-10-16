package kr.ac.kopo.springboot_notice.controller;

import kr.ac.kopo.springboot_notice.dto.NoticeDTO;
import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import kr.ac.kopo.springboot_notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/messages")

public class NoticeController {

    @Autowired
    private NoticeRepository noticeRepository;

    @PostMapping
    public NoticeDTO updateAndSaveMessage(@RequestBody NoticeDTO noticeDTO) {
        // 메시지를 수정하는 로직 (예: 메시지에 접미사 추가)
        String modifiedMessage = noticeDTO.getMessage() + " (수정됨)";
        System.out.println(modifiedMessage);


        // 수정된 메시지를 데이터베이스에 저장
        NoticeEntity messageEntity = new NoticeEntity();
        messageEntity.setMessage(modifiedMessage);
        noticeRepository.save(messageEntity);

        // 수정된 메시지를 다시 클라이언트로 반환
        NoticeDTO response = new NoticeDTO();
        response.setMessage(modifiedMessage);
        return response;
    }
}

