package com.pji2918.springdeveloper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 멤버 테이블에 있는 모든 레코드를 읽어 반환
    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // SELECT * FROM member;
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
}
