package kr.ac.kopo.springboot_notice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDTO {
    // 어떤 데이터를 받아올 건지 선언
    private String message;


    // 받아온 데이터를 외부로 리턴
    public String getMessage() {
        return message;
    }

    // 외부 데이터를 내부로 전환
    public void setMessage(String message) {
        this.message = message;
    }

}
