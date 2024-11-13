package jpabook.jpa_bookshop.service;

import jpabook.jpa_bookshop.domain.Delivery;
import jpabook.jpa_bookshop.domain.Member;
import jpabook.jpa_bookshop.domain.Order;
import jpabook.jpa_bookshop.domain.item.Item;
import jpabook.jpa_bookshop.repository.ItemRepository;
import jpabook.jpa_bookshop.repository.MemberRepository;
import jpabook.jpa_bookshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /*
    * 주문하기
    * */

    @Transactional
    public Long order(Long memberId,Long itemId,int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 생성
        Order order = Order.createOrder(member, delivery, item.getOrderItems());

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /*
    * 주문 취소
    * */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 조회
        Order findOrder = orderRepository.findOne(orderId);
        //주문 취소
        findOrder.cancel();
    }


    /*
    * 주문 검색
    * */
  /*  public List<Order> findOrders(Order order){

    }*/


}
