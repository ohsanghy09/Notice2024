package kr.ac.kopo.springboot_notice.controller;


import kr.ac.kopo.springboot_notice.dto.CommentDTO;
import kr.ac.kopo.springboot_notice.entity.CommentEntity;
import kr.ac.kopo.springboot_notice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "http://localhost:8080")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 공지사항 저장
    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestBody CommentEntity commentEntity){
        commentService.addComment(commentEntity);
        return ResponseEntity.ok("댓글이 등록되었습니다.");
    }

    // 댓글 조회
    @PostMapping("/get")
    public ResponseEntity<List<CommentDTO>> getComments(
            @RequestBody Map<String, String> request) {
        String noticeId = request.get("noticeId");
        String userId = request.get("userId");

        // 댓글 데이터 가져오기
        List<CommentDTO> comments = commentService.getComments(noticeId, userId);

        // Vue로 댓글 리스트 반환
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PostMapping("/update")
    public ResponseEntity<String> updateComment(@RequestBody CommentDTO commentDTO) {
        commentService.updateComment(commentDTO); // Service 호출
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

    // 댓글 삭제
    @PostMapping("/delete")
    public ResponseEntity<String> deleteComment(@RequestBody CommentDTO commentDTO){
        commentService.deleteComment(commentDTO);
        return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
    }
}
