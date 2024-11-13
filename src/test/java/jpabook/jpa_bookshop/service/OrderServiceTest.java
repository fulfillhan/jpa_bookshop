package jpabook.jpa_bookshop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpa_bookshop.domain.Address;
import jpabook.jpa_bookshop.domain.Member;
import jpabook.jpa_bookshop.domain.Order;
import jpabook.jpa_bookshop.domain.OrderStatus;
import jpabook.jpa_bookshop.domain.item.Book;
import jpabook.jpa_bookshop.domain.item.Item;
import jpabook.jpa_bookshop.exception.NotEnoughStockException;
import jpabook.jpa_bookshop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //회원과 상품 생성
        Member member = getMember();
        Item book = getItem("책이름", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는");
        assertEquals(1, getOrder.getCount(), "상품 주문시 상품 종류수");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "상품 주문 가격 * 수량");
        assertEquals(8, book.getStockQuantity(), "상품 재고수량은");
    }

    private Item getItem(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "광명시", "1230"));
        em.persist(member);
        return member;
    }

    @Test
    public void 주문취소() throws Exception {
        //given (준비)
        Member member = getMember();
        Item book = getItem("책이름", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when (실행)
        orderService.cancelOrder(orderId);
        //then (검증)

        Order cancelOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, cancelOrder.getStatus(), "주문 취소시 상태");
        assertEquals(10, book.getStockQuantity(), "주문이 취소되면 재고 수량은 변함이 없어야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과여부() throws Exception {
        Member member = getMember();
        Item book = getItem("책이름", 10000, 10);

        int orderCount = 11;
        //orderService.order(member.getId(), book.getId(), orderCount); // 예외 발생해야함
        assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), orderCount)).isInstanceOf(NotEnoughStockException.class);

    }


}