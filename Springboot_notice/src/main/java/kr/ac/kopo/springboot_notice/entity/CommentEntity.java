package kr.ac.kopo.springboot_notice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String noticeId;    // 게시판 아이디

    private String userId;      // 댓글을 작성하는 회원 아이디

    private String content;     // 댓글 내용

    private String time;        // 댓글을 작성한 시간
}
