package jpabook.jpa_bookshop.service;

import jpabook.jpa_bookshop.domain.Member;
import jpabook.jpa_bookshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //메서드모두 기본적으로 적용된다.
@RequiredArgsConstructor   //final 로 선언된 필드만 생성자로 만들어준다.
//@AllArgsConstructor 모든 필드에 생성자를 만들어준다.
public class MemberService {


    /*
    * 필드 주입: 강제성을 띄우고 있어 사용하기 용이하지 않다.
    * setter 주입 :  변경 가능성이 있는 의존관계에 사용하는 것이 좋다. 불변성을 보장받지 못한다.
    * 생성자 주입: 1번 생성되기 때문에 불변성을 보장한다. 다른데서 사용하게 될때 컴파일오류로 데이터 누락방지한다.
    *     * */
    private final MemberRepository memberRepository;

    /*
    * 회원 가입
    * */
    @Transactional
    public Long join(Member member) {
        //회원 중복 체크
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // 영속성내에는 db에 insert 되기전 pk값은 가지기 때문에 조회 가능하다.
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
    * 전체 회원 조회
    * */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /*
    * 회원 단건 조회
    * */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
