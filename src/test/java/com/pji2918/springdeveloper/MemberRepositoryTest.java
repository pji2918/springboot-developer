package com.pji2918.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("member 테이블의 모든 레코드 가져오기")
    @Sql("/insert-members.sql")
    public void getAllMembers() {
        // Given: 필요 없음. @Sql에서 처리됨.

        // When
        List<Member> members = memberRepository.findAll();

        // Then
        assertThat(members).size().isEqualTo(3);
    }

    @Test
    @DisplayName("ID가 2인 레코드 가져오기")
    @Sql("/insert-members.sql")
    public void getMemberById2() {
        // When
        Optional<Member> member = memberRepository.findById(2L);

        // Then
        assertThat(member).isPresent();
        assertThat(member.get().getId()).isEqualTo(2L);
        assertThat(member.get().getName()).isEqualTo("B");
    }
}