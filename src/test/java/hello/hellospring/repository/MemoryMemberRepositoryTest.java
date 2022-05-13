package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    /*
    테스트는 메소드 실행 순서와 독립적으로 설계되어야 한다.
    메소드 끝날 떄 마다 공용 데이터 지워줌
  줌  */
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
      Member member = new Member();
      member.setName("Marie");
      repository.save(member);
      Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("Marie");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Zuno");
        repository.save(member2);

        Member result = repository.findByName("Marie").get();
        assertThat(member1).isEqualTo(result);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("Marie");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Jojo");
        repository.save(member2);

        List<Member> result =  repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
