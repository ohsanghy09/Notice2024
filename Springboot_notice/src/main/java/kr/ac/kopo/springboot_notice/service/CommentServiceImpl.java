package kr.ac.kopo.springboot_notice.service;

import jakarta.transaction.Transactional;
import kr.ac.kopo.springboot_notice.dto.CommentDTO;
import kr.ac.kopo.springboot_notice.entity.CommentEntity;
import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import kr.ac.kopo.springboot_notice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    // 데이터 베이스 관련 repository 객체 생성
    @Autowired
    private CommentRepository commentRepository;

    // 댓글 저장
    @Override
    public CommentEntity addComment(CommentEntity commentEntity){
        return commentRepository.save(commentEntity);
    }


    // 시간별로 댓글 조회
    @Override
    public List<CommentDTO> getComments(String noticeId, String userId) {
        // 1. 해당 게시판 ID로 댓글 검색 및 시간순 정렬
        List<CommentEntity> comments = commentRepository.findByNoticeIdOrderByTimeAsc(noticeId);

        // 2. 댓글 데이터를 DTO로 변환
        List<CommentDTO> commentDTOs = comments.stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),           // 댓글 고유 ID
                        comment.getNoticeId(),
                        comment.getUserId(),
                        comment.getContent(),
                        comment.getTime()
                ))
                .collect(Collectors.toList());


        return commentDTOs;
    }

    @Override
    public void updateComment(CommentDTO commentDTO){

        Optional<CommentEntity> optionalComment = commentRepository.findById(commentDTO.getId());


        CommentEntity Comment = optionalComment.get();
        // 댓글 내용 업데이트
        Comment.setContent(commentDTO.getContent());
        commentRepository.save(Comment); // 업데이트 후 저장
    }

    @Override
    public void deleteComment(CommentDTO commentDTO) {

        // ID로 댓글이 존재하는지 확인
        Optional<CommentEntity> optionalComment = commentRepository.findById(commentDTO.getId());


        CommentEntity Comment = optionalComment.get();
        // 댓글 삭제
        commentRepository.deleteById(Comment.getId());
    }

    // 해당 게시판이 삭제되면 게시판 관련 댓글 삭제
    @Override
    @Transactional // 트랜잭션 활성화
    public void deleteAllNoticesByNoticeId(String noticeId) {
        commentRepository.deleteByNoticeId(noticeId);
    }


}
