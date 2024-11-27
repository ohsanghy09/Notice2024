package kr.ac.kopo.springboot_notice.controller;


import kr.ac.kopo.springboot_notice.entity.CommentEntity;
import kr.ac.kopo.springboot_notice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 공지사항 저장
    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestBody CommentEntity commentEntity){
        commentService.addComment(commentEntity);
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }


}
