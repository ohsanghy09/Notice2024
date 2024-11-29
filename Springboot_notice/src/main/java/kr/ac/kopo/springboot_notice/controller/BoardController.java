package kr.ac.kopo.springboot_notice.controller;

import kr.ac.kopo.springboot_notice.dto.SearchDTO;
import kr.ac.kopo.springboot_notice.entity.BoardEntity;
import kr.ac.kopo.springboot_notice.entity.NoticeEntity;
import kr.ac.kopo.springboot_notice.service.BoardService;
import kr.ac.kopo.springboot_notice.service.CommentService;
import kr.ac.kopo.springboot_notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@CrossOrigin(origins = "http://localhost:3000")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    // 공지사항 수정
    @PostMapping("/update")
    public ResponseEntity<BoardEntity> updateBoard(@RequestBody BoardEntity boardEntity) {
        BoardEntity updatedMessage = boardService.updateBoard(boardEntity);
        return ResponseEntity.ok(updatedMessage);
    }

    // 공지사항 삭제
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteNoticeById(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        boardService.deleteNoticeById(id);
        commentService.deleteAllNoticesByNoticeId(String.valueOf(id));
        return ResponseEntity.ok().build();
    }

    // 공지사항 등록
    @PostMapping("/add")
    public ResponseEntity<BoardEntity> addNotice(@RequestBody BoardEntity boardEntity) {
        BoardEntity savedMessage = boardService.addNotice(boardEntity);
        return ResponseEntity.ok(savedMessage);
    }

    // 공지사항 전체 삭제
    @PostMapping("/deleteAll")
    public ResponseEntity<Void> deleteNoticesByWriter(@RequestBody Map<String, String> request) {
        String writer = request.get("writer"); // 요청 본문에서 작성자(writer) 가져오기
        try {

            if(boardService.writerExists(writer)){
                // 작성자를 기준으로 게시판 ID 추출
                List<Long> boardIds = boardService.getBoardIdsByWriter(writer);


                // 추출한 게시판 ID로 댓글 삭제
                for (Long id : boardIds) {
                    System.out.println("★★★★" + id);
                    commentService.deleteAllNoticesByNoticeId(id.toString());
                }

                // 게시판 삭제
                boardService.deleteRecordsByWriter(writer);

                return ResponseEntity.ok().build();

            }else {
                return ResponseEntity.noContent().build(); // 204 No Content 반환
            }


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 오류 시 500 반환
        }
    }

    // 공지사항 개수 반환
    @GetMapping("/count")
    public ResponseEntity<Long> countNotice(){
        long count = boardService.getNoticeCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    //시작페이지에 따른 공지사항 목록 조회
    @PostMapping("/getByStart")
    public ResponseEntity<List<BoardEntity>> getNoticesByStart(@RequestBody Map<String, Integer> request) {
        int start = request.get("start");  // 클라이언트에서 전송한 start 값

        List<BoardEntity> boards = boardService.getNoticesByPageAndDate(start);
        return ResponseEntity.ok(boards);  // 정렬 및 페이징된 공지사항 반환
    }

    // 선택된 옵션과 검색어로 전체 데이터 개수 조회
    @PostMapping("/searchCount")
    public ResponseEntity<Long> getSearchCount(@RequestBody SearchDTO searchDTO){
        Long count = boardService.getNoticeCountBySearch(searchDTO.getOption(), searchDTO.getText());
        return ResponseEntity.ok(count);
    }

    // 선택된 옵션과 검색어로 전체 공지사항 조회
    @PostMapping("/searchNotice")
    public ResponseEntity<List<BoardEntity>> searchNotices(@RequestBody SearchDTO searchDTO ) {
        List<BoardEntity> boards = boardService.searchNotices(
                searchDTO.getOption(),
                searchDTO.getText(),
                searchDTO.getStart()
        );
        return ResponseEntity.ok(boards);
    }




}

