package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.entity.CommentEntity;

public interface CommentService {

    // 댓글 내용 추가
    CommentEntity addComment(CommentEntity commentEntity);

}
