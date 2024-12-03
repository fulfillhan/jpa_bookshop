package jpabook.jpa_bookshop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpa_bookshop.domain.Member;
import jpabook.jpa_bookshop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional  // 테스트 케이스에서 기본적으로 테스트 시작하면 커밋, 종료되면 롤백한다.
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepository;
    @Autowired
    EntityManager em;


    //@Rollback(value = false)
    /*insert 문이 확인 안됨!! 확인 하는 방법
     * 기본적으로 트랜잭션은 테스트코드에서 테스트를 실행할 때 커밋되고, 종료되면 롤백되버린다.
     * Rollback(value = false) 메서드에 사용하거나  EntityManager 주입받아 em.flush() 한다.
     * */
    @Test
    @Rollback(value = false)
    public void joinMember() throws Exception{
        //given 주어졌을 때(테스트의 전제조건)
        Member member = new Member();
        member.setName("kim");

        //when ~이렇게 실행할 때(지정)
        Long savedId = memberService.join(member);

        //then 결과(지정된 동작으로 인한 결과 예상)
        //em.flush();   영속성 컨텍스트에 있는 데이터를 db에 반영한다.
        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test
    public void checkValidateMember() throws Exception{
        //given 주어졌을 때
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        //when ~이렇게 실행할 때
        memberService.join(member1);
        assertThatThrownBy(() -> memberService.join(member2)).isInstanceOf(IllegalStateException.class);

        //then 결과
        fail("예외가 발생해야 한다!!");// 테스트 코드가 완전히 진행되지 않았을 때
    }

}