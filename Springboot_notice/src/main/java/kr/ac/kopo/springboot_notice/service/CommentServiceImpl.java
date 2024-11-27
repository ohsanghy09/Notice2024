package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.dto.CommentDTO;
import kr.ac.kopo.springboot_notice.entity.CommentEntity;
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

        // 3. 회원 ID를 기준으로 댓글 이동
        if (userId != null && !userId.isEmpty()) {
            Optional<CommentDTO> matchingComment = commentDTOs.stream()
                    .filter(comment -> comment.getUserId().equals(userId))
                    .findFirst();

            matchingComment.ifPresent(comment -> {
                commentDTOs.remove(comment);
                commentDTOs.add(0, comment); // 해당 댓글을 리스트의 맨 앞으로 이동
            });
        }

        return commentDTOs;
    }


}
