package jpabook.jpa_bookshop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpa_bookshop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    //save
    public void save(Order order){
        em.persist(order);
    }

    //findOne
    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

  /* TODO findAll -> 검색기능 추가
    public List<Order> findAll(){}*/


}
