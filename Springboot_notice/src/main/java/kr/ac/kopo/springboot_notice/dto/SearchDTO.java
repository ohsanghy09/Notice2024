package kr.ac.kopo.springboot_notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchDTO {

    private String option; // 검색 옵션 (제목, 작성자 등)
    private String text; // 검색어
}
