package kr.ac.kopo.springboot_notice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {

    private Long id;          // 댓글 고유 ID

    private String noticeId;    // 게시판 아이디

    private String userId;      // 댓글을 작성하는 회원 아이디

    private String content;     // 댓글 내용

    private String time;        // 댓글을 작성한 시간


}
