package jpabook.jpa_bookshop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpa_bookshop.domain.Order;
import jpabook.jpa_bookshop.domain.OrderSearch;
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
    
    
    //전체 조회-> 검색 기능
    public List<Order> findAll(OrderSearch orderSearch){
        //동적 쿼리 필요 
        //값이 다 있는 경우
        List<Order> resultList = em.createQuery("select o from Order o join o.member m " +
                "where o.status = :status and m.name = :name", Order.class)
                //객체이기 때문에 참조하는 스타일로 조인해야 한다.
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                //.setFirstResult(1)
                .setMaxResults(1000) //최대 1000건
                .getResultList();
        return resultList;
        
    }
}
