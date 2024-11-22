package jpabook.jpa_bookshop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpa_bookshop.domain.*;
import jpabook.jpa_bookshop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/*
 * 샘플 데이터
 * */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct  // 빈 초기화 -> 따로 분리한 이유: 트랜잭션이 같이 적용되지 않기 때문에
    public void itint(){
        initService.dbInit();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Member member = createMember("userA","서울","1","1111");
            em.persist(member);

            Book book1 = createBook("JPA1 Book",10000,100);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book",20000,100);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);

            List<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem1);
            orderItems.add(orderItem2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItems);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB","경기도","2","2222");
            em.persist(member);

            Book book1 = createBook("Spring1 Book",20000,200);
            em.persist(book1);

            Book book2 = createBook("Spring2 Book",40000,300);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            List<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(orderItem1);
            orderItems.add(orderItem2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItems);
            em.persist(order);
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private static Member createMember(String name,String city,String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city,street,zipcode));
            return member;
        }
    }
}
