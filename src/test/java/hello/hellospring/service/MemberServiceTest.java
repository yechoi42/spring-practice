package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        //DI = Dependency Injection, 리포지터리를 넣어줌
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("ㅋㅋㅋ");

        //when
        Long saveId = memberService.join(member);

        //then
        Member one = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(one.getName());
    }

    @Test
    void duplicatedMemberError() {
        //given
        Member member1 = new Member();
        member1.setName("ㅋㅋㅋ");

        Member member2 = new Member();
        member2.setName("ㅋㅋㅋ");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e ){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("Marie");

        Member member2 = new Member();
        member2.setName("Gongzu");

        memberService.join(member1);
        memberService.join(member2);
        List<Member> members = memberService.findMembers();
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        Member member1 = new Member();
        member1.setName("Marie");

        Member member2 = new Member();
        member2.setName("Gongzu");

        memberService.join(member1);
        memberService.join(member2);
        Member member = memberService.findOne(member2.getId()).get();
        assertThat(member).isEqualTo(member2);
    }
}