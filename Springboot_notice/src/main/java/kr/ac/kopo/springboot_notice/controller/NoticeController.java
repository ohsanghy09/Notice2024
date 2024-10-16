package kr.ac.kopo.springboot_notice.controller;

import kr.ac.kopo.springboot_notice.dto.NoticeDTO;
import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import kr.ac.kopo.springboot_notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/posts")

public class NoticeController {

    @Autowired
    private NoticeRepository noticeRepository;

    @PostMapping
    public NoticeEntity createPost(@RequestBody NoticeDTO noticeDTO) {
        NoticeEntity notice = new NoticeEntity();
        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());
        return noticeRepository.save(notice);
    }
}

