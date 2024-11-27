package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.dto.CommentDTO;
import kr.ac.kopo.springboot_notice.entity.CommentEntity;

import java.util.List;

public interface CommentService {

    // 댓글 내용 추가
    CommentEntity addComment(CommentEntity commentEntity);

    List<CommentDTO> getComments(String noticeId, String userId);

}
