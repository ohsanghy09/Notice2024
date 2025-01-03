package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.dto.CommentDTO;
import kr.ac.kopo.springboot_notice.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    // 댓글 내용 추가
    CommentEntity addComment(CommentEntity commentEntity);

    // 댓글 조회
    List<CommentDTO> getComments(String noticeId, String userId);

    // 댓글 수정
    void updateComment(CommentDTO commentDTO);

    // 댓글 삭제
    void deleteComment(CommentDTO commentDTO);

    // 게시판 삭제되면 해당 게시판 댓글 삭제
    void deleteAllNoticesByNoticeId(String noticeId);

}
