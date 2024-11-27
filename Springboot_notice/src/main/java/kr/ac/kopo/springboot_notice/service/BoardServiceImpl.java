package kr.ac.kopo.springboot_notice.service;

import kr.ac.kopo.springboot_notice.entity.BoardEntity;
import kr.ac.kopo.springboot_notice.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService{

    // 데이터 베이스 관련 repository 객체 생성
    @Autowired
    private BoardRepository boardRepository;

    // 공지사항 내용 수정
    @Override
    public BoardEntity updateBoard(BoardEntity boardEntity) {

        // 받아온 데이터 아이디를 내부 변수로 리턴 후 데이터베이스에서 검색하여 optional entity 변수에 저장
        Optional<BoardEntity> optionalBoard = boardRepository.findById(boardEntity.getId());

        // 해당 아이디에 대한 내용이 optional entity에 없으면 동작하는 조건
        if (!optionalBoard.isPresent()) {
            throw new RuntimeException("해당 공지사항을 찾을 수 없습니다.");
        }

        // 수정 로직
        BoardEntity board = optionalBoard.get();   // 수정하여 데이터베이스에 저장할 entity에 기존 데이터베이스에 있는 데이터를 선언 -> get
        board.setTitle(boardEntity.getTitle());   // 외부에서 받은 제목을 데이터베이스에 저장하기 위한 entity에 저장(변경)
        board.setContent(boardEntity.getContent()); // 외부에서 받은 내용을 데이터베이스에 저장하기 위한 entity에 저장(변경)
        board.setTime(boardEntity.getTime()); // 외부에서 받은 시간을 데이터베이스에 저장하기 위한 entity에 저장(변경)
        return boardRepository.save(board);  // 수정된 데이터를 저장 후 반환
    }

    // 공지사항 삭제
    @Override
    public void deleteNoticeById(Long id) { // 외부로부터 id 값 받음
        Optional<BoardEntity> optionalBoard = boardRepository.findById(id); // 데이터베이스에서 아이디 검색 후 optional entity에 저장

        // 해당 아이디에 대한 내용이 optional entity에 있으면 공지사항 삭제
        if (optionalBoard.isPresent()) {
            boardRepository.deleteById(id);  // 공지사항 삭제
        } else {
            throw new RuntimeException("해당 ID의 공지사항을 찾을 수 없습니다.");
        }
    }

    // 공지사항 추가
    @Override
    public BoardEntity addNotice(BoardEntity boardEntity) {
        return boardRepository.save(boardEntity);  // 데이터베이스에 공지사항 저장
    }

    // 공지사항 전체 삭제
    @Override
    public void deleteNoticeAll(){
        boardRepository.deleteAll();
    }

    // 공지사항을 writer를 기준으로 전체 삭제
    @Override
    public void deleteRecordsByWriter(String writer) {
        boardRepository.deleteByWriter(writer);
    }

    @Override
    public long getNoticeCount(){
        return boardRepository.countNotice();
    }

    @Override
    public List<BoardEntity> getNoticesByDate(int start){
        return boardRepository.findNoticesByDate(start);
    }

    @Override
    public Long getNoticeCountBySearch(String option, String text) {
        // 검색 옵션에 따라 레파지토리의 다른 쿼리를 호출
        switch (option) {
            case "제목":
                return boardRepository.countByTitleContaining(text);
            case "작성자":
                return boardRepository.countByWriterContaining(text);
            case "내용":
                return boardRepository.countByContentContaining(text);
            default:
                return 0L;
        }
    }

    @Override
    public List<BoardEntity> searchNotices(String option, String text, int start) {
        // 검색 옵션에 따른 레파지토리 호출
        Pageable pageable = PageRequest.of(start / 10, 10, Sort.by("time").descending()); // 날짜 기준 내림차순 정렬 및 페이지네이션

        switch (option) {
            case "제목":
                return boardRepository.findByTitleContaining(text, pageable);
            case "작성자":
                return boardRepository.findByWriterContaining(text, pageable);
            case "내용":
                return boardRepository.findByContentContaining(text, pageable);
            default:
                return Collections.emptyList();
        }
    }

    // 전체 공지사항 페이지네이션 조회
    @Override
    public List<BoardEntity> getNoticesByPageAndDate(int start) {
        // 페이지네이션 및 문자열 기반 정렬 설정
        Pageable pageable = PageRequest.of((start - 1) / 10, 10, Sort.by(Sort.Direction.DESC, "time"));

        // JPA가 문자열로 정렬하여 Page 객체 반환
        Page<BoardEntity> page = boardRepository.findAllByOrderByTimeDesc(pageable);

        return page.getContent();  // Page 객체에서 실제 데이터 추출
    }

}
