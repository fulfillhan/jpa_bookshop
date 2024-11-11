package jpabook.jpa_bookshop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpa_bookshop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  //컴포넌트 스캔대상이 되어 스프링 빈으로 등록된다.
@RequiredArgsConstructor
public class MemberRepository {

    /*@PersistenceUnit : 엔티티 메니저 팩토리 주입*/
   // @PersistenceContext /JPA EntityManager 주입
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }


}
