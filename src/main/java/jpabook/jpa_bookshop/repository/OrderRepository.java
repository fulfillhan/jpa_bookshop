package jpabook.jpa_bookshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpa_bookshop.domain.Member;
import jpabook.jpa_bookshop.domain.Order;
import jpabook.jpa_bookshop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
/*
    public List<Order> findAll(OrderSearch orderSearch){
        //동적 쿼리 필요 
        //값이 다 있는 경우
        */
/*List<Order> resultList = em.createQuery("select o from Order o join o.member m " +
                "where o.status = :status and m.name = :name", Order.class)
                //객체이기 때문에 참조하는 스타일로 조인해야 한다.
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                //.setFirstResult(1)
                .setMaxResults(1000) //최대 1000건
                .getResultList();*//*

        return resultList;
        
    }
*/

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName()
                            + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000
        return query.getResultList();
    }
}
