package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.entity.CommentEntity;
import kr.ac.kopo.springboot_notice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    // 데이터 베이스 관련 repository 객체 생성
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentEntity addComment(CommentEntity commentEntity){
        return commentRepository.save(commentEntity);
    }


}
