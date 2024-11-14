package jpabook.jpa_bookshop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpa_bookshop.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Transactional
    void update(Member member){  //member: 준영속 엔티티
        Member findMember = em.find(Member.class, member.getId());// 식별자를 사용해 영속성 엔티티 findMember 조회

        //변경 필요한 필드 변경하기
        findMember.setName(member.getName());

    }

    @Transactional
    void merge(Member member){//member: 준영속 엔티티
        Member mergeMember = em.merge(member);
    }
}
