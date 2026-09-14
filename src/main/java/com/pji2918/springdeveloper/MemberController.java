package com.pji2918.springdeveloper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 요청을 받아서 적절한 비즈니스 로직으로 연결
    // http://localhost:8080/member 요청과 메서드를 연결
    @GetMapping("/api/members")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping("/api/members")
    public boolean createMember(@RequestBody MemberCreationDto member) {
        memberService.createMember(member);
        return true;
    }
}
