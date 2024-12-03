package jpabook.jpa_bookshop.repository;

import jpabook.jpa_bookshop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {
    //자동으로 해준다.
    //SELECT m FRO MEMBER m WHERE  m.name = ?
    List<Member> findByName(String name);
}
