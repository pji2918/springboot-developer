package com.pji2918.springdeveloper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    // 요청을 받아서 적절한 비즈니스 로직으로 연결
    // http://localhost:8080/member 요청과 메서드를 연결
    @GetMapping("/member")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping("/member")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveMember(member));
    }
}
