package jpabook.jpa_bookshop.api;

import jpabook.jpa_bookshop.domain.Order;
import jpabook.jpa_bookshop.domain.OrderItem;
import jpabook.jpa_bookshop.domain.OrderSearch;
import jpabook.jpa_bookshop.repository.OrderRepository;
import jpabook.jpa_bookshop.repository.order.query.OrderQueryDto;
import jpabook.jpa_bookshop.repository.order.query.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
        private final OrderRepository orderRepository;
        private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            //lazy 강제 초기화
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                orderItem.getItem().getName();
            }

        }
        return all;
    }
    /*
    * 엔티티 -> dto 변경하기
    * */

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        return orders.stream().map(order -> new OrderDto(order)).collect(toList());
    }

    /*
    엔티티 -> dtp 변경(페치 조인 적용)
    *
    * 1:n 이면 n 만큼 데이터가 나온다. -> 데이터 뻥튀기 즉, 중복되 row 조회
    *단점 :  페이징 불가!
    * */

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
         List<Order> orders = orderRepository.findOrdersWithItems();
        return orders.stream().map(order -> new OrderDto(order)).collect(toList());
    }

    /*
    * 페이징 고려하기
    * toOne관계에서는 우선 모두 페치 조인으로 최적화
    * 컬렉션 관계에서는 batch_size설정
    * */

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_1(@RequestParam(defaultValue = "0") int offset,
                                     @RequestParam(defaultValue = "100") int limit){
         List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
         return orders.stream().map(order -> new OrderDto(order)).collect(toList());
    }

    /*
    * JPA->DTO 로 변환하기
    * */

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }

    /*
     * JPA->DTO 로 변환하기(성능 최적화하기)
     * */
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        return orderQueryRepository.findAllByDto_optimization();
    }


}
