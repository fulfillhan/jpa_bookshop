package jpabook.jpa_bookshop.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import jpabook.jpa_bookshop.repository.OrderSimpleDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Data
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;


    public List<OrderSimpleQueryRepository> findOrderDto() {
        em.createQuery("select new jpabook.jpa_bookshop.repository.order.simplequery.OrderSimpleQueryRepository(o.id,m.name,o.orderDate,o.status,o.address) from Order o " +
                "join o.member m" +
                "join o.delivery d", OrderSimpleDto.class);
        return null;
    }


}
