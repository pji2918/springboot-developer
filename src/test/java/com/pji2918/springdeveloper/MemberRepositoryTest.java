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
    @Sql("/insert-members.sql") // given
    public void getAllMembers() {
        // Given: 필요 없음. @Sql에서 처리됨.

        // When
        List<Member> members = memberRepository.findAll();

        // Then
        assertThat(members).size().isEqualTo(3);
    }

    @Test
    @DisplayName("ID가 2인 레코드 가져오기")
    @Sql("/insert-members.sql") // given
    public void getMemberById2() {
        // When
        Optional<Member> member = memberRepository.findById(2L);

        // Then
        assertThat(member).isPresent();
        assertThat(member.get().getId()).isEqualTo(2L);
        assertThat(member.get().getName()).isEqualTo("B");
    }

    @Test
    @DisplayName("이름이 B인 레코드 가져오기")
    @Sql("/insert-members.sql") // given
    public void getMemberByName() {
        // when
        List<Member> members = memberRepository.findByName("B");

        // then
        assertThat(members).isNotEmpty();
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.getFirst().getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("이름이 B인 레코드 가져오기")
    @Sql("/insert-members.sql") // given
    public void getMemberByName2() {
        // when
        Optional<Member> member = memberRepository.findFirstByName("B");

        // then
        assertThat(member).isPresent();
        assertThat(member.get().getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("하나의 Member 객체를 DB에 저장하기")
    public void saveMember() {
        // given (준비)
        Member member = new Member("A");
        memberRepository.save(member);

        // when (실행)
        List<Member> members = memberRepository.findAll();

        // then (검증)
        assertThat(members).isNotEmpty();
        assertThat(members.getFirst().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("3개의 Member 객체를 DB에 저장하기")
    public void saveMembers() {
        // given
        List<Member> members = List.of(new Member("A1"), new Member("A2"), new Member("A3"));

        // when
        memberRepository.saveAll(members);

        // then
        assertThat(memberRepository.findAll()).size().isEqualTo(3);
        assertThat(memberRepository.findAll()).satisfiesExactly(
                member1 -> assertThat(member1.getName()).isEqualTo("A1"),
                member2 -> assertThat(member2.getName()).isEqualTo("A2"),
                member3 -> assertThat(member3.getName()).isEqualTo("A3")
        );
    }

    @Test
    @DisplayName("ID가 2인 레코드 삭제하기")
    @Sql("/insert-members.sql") // given
    public void deleteMemberById() {
        // when
        memberRepository.deleteById(2L);

        // then
        assertThat(memberRepository.findById(2L)).isEmpty();
    }

    @Test
    @DisplayName("모든 레코드 삭제하기")
    @Sql("/insert-members.sql") // given
    public void deleteAllMembers() {
        // when
        memberRepository.deleteAll();

        // then
        assertThat(memberRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Dirty-Checking 기능을 이용해 ID가 2인 레코드의 Name을 홍길동으로 바꾸기")
    @Sql("/insert-members.sql")
    public void updateMember() {
        //noinspection OptionalGetWithoutIsPresent
        memberRepository.findById(2L).get().setName("홍길동");
        Optional<Member> member = memberRepository.findById(2L);

        assertThat(member).isPresent();
        assertThat(member.get().getName()).isEqualTo("홍길동");
    }
}