package jpabook.jpa_bookshop.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Data
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;


    public List<OrderSimpleQueryDto> findOrderDto() {
        return em.createQuery("select new jpabook.jpa_bookshop.repository.order.simplequery.OrderSimpleQueryDto(o.id,m.name,o.orderDate,o.status,d.address)" +
                "from Order o"+
                "join o.member m"+
                "join o.delivery d",OrderSimpleQueryDto.class).getResultList();
    }
}
