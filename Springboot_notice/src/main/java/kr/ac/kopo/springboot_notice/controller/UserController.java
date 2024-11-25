package kr.ac.kopo.springboot_notice.controller;


import kr.ac.kopo.springboot_notice.dto.UserDTO;
import kr.ac.kopo.springboot_notice.service.NoticeService;
import kr.ac.kopo.springboot_notice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;


    // 아이디 중복 확인
    @PostMapping("/checkId")
    public ResponseEntity<Map<String, Object>> checkUser(@RequestBody UserDTO userDTO) {

        // 데이터 베이스에 해당 아이디가 존재하는지 boolean 형식으로 변수에 반환
        boolean exists = userService.checkIfUserExists(userDTO.getUserId());

        // response data 정의 객체 선언(response)
        Map<String, Object> response = new HashMap<>();

        // 만약 데이터베이스에 사용자가 입력한 아이디가 존재하지 않을 경우
        if (!exists) {
            response.put("status", "OK");
            response.put("message", "해당 아이디를 사용할 수 있습니다.");
            return ResponseEntity.ok(response);
        }
        // 만약 데이터베이스에 사용자가 입력한 아이디가 존재하지 않을 경우
        else {
            response.put("status", "NO");
            response.put("message", "다른 아이디를 사용해주시기 바랍니다.");
            return ResponseEntity.ok(response);
        }
    }

    // 회원가입
    @PostMapping("/AddUser")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO){

        userService.saveUser(userDTO);
        return ResponseEntity.ok("회원가입에 성공하였습니다.");
    }


} // main
