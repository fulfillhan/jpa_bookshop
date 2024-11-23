package jpabook.jpa_bookshop.api;

import jpabook.jpa_bookshop.domain.Address;
import jpabook.jpa_bookshop.domain.Order;
import jpabook.jpa_bookshop.domain.OrderSearch;
import jpabook.jpa_bookshop.domain.OrderStatus;
import jpabook.jpa_bookshop.repository.OrderRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/*
*  @ManyToOne ,  @OneToOne 관계 최적화
* */
@Controller
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /*
    V1
    엔티티 직접 노출
    무한 루프에 빠지고있다..
       -양방향 관계 문제 발생 ->  @JsonIgnore
       - Hibernate5Module 모듈 등록, json으로 할 때 LAZY=null 처리
    * */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();// LAZY 강제 초기화
            order.getDelivery().getAddress();//LAZY 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/V2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        //n+1문제 -> 1+회원 n+배원 n
        List<SimpleOrderDto> result = orderRepository.findAllByCriteria(new OrderSearch()).stream()
                .map(order -> new SimpleOrderDto(order)) //order-> SimpleOrderDto 로 변경한다.
                .collect(toList()); // 반복하면서 다시 list로 변경해서 생성한다.
        return result;
    }


    //dto 클래스 생성
    @Data
    static class SimpleOrderDto {
        private Long id;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        /*
        * order를 매개변수로 가져와서 바로 생성자를 통해 변환
        * */
        public SimpleOrderDto(Order order){
            id = order.getId();
            name = order.getMember().getName(); //lazy 초기화.
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
