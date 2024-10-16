package kr.ac.kopo.springboot_notice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class NoticeDTO {


    private String title; // 제목

    private String content; // 내용

    private String writer; // 작성자

    private String time; // 작성 및 수정 시간

}
