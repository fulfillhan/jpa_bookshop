package jpabook.jpa_bookshop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpa_bookshop.domain.Member;
import jpabook.jpa_bookshop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    
    /* saveMemberV1
    * 문제점 1 : saveMemberV1 에 요청파라미터로 엔티티를 직접 사용한다.
    * 검증 로직이 들어감으로써 프레젠테이션 계층은 엔티티와 강한 관계를 갖는다.
    * API를 사용할 때 엔티티는 다양하게 사용되기때문에 하나의 엔티티에 각각의 API를 위한 모든 요구사항을 담기는 어렵다. 
    * -> 엔티티가 변경되면 API스펙이 변경된다.
    *
    * 문제점2 : 파라미터로 엔티티가 있으면 어떤게 넘어오는지 알 수가 없다.
    * 결국 사이드 이펙트 발생!!!!!!!!
    *
    * 해결점 : API요청 스펙에 맞추어 DTO를 파라미터로 받는다.
    * */
    
    @PostMapping("/api/V1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }
    
    /*
    * saveMemberV2
    * 엔티티 대신에  API 스펙에 맞는 DTO - CreateMemberRequest 별도 객체 사용
    * 엔티티를 외부에 노출하거나 파라미터로 받는 것은 하지 말자!!! - 사이드 이펙트 방지!
    * */
    @PostMapping("/api/V2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    
    @Data
    static class CreateMemberResponse {
        private Long id;
        public CreateMemberResponse(Long name) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest{
        @NotEmpty  //엔티티가 아닌 dto에서 검증설정을 할 수 있다.
        private String name;

    }

}
