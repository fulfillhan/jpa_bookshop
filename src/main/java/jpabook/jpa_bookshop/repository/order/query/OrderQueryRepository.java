package jpabook.jpa_bookshop.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    /*
    * 컬렉션은 별도로 조회
    * */

    public List<OrderQueryDto> findOrderQueryDtos(){
        //toOne 코드를 모두 한번에 조회
        List<OrderQueryDto> result = findOrders();

        //루프 돌면서 컬렉션 추가 -> 직접 채운다.
        result.forEach(orderQueryDto -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(orderQueryDto.getOrderId());
            orderQueryDto.setOrderItems(orderItems);
        });
        return result;

    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpa_bookshop.repository.order.query.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count)"+
                "from OrderItem oi"+
                "join oi.item i"+
                "where oi.order.id = :orderId",OrderItemQueryDto.class).getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery("select new jpabook.jpa_bookshop.repository.order.query.OrderQueryDto(o.id,m.name,o.orderDate,o.status,o.address)" +
                "from Order o" +
                "join o.member m" +
                "join o.delivery d", OrderQueryDto.class).getResultList();
    }

    /*
    * 최적화
    * query 1번, 컬렉션 1번
    * */

    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        //orderItem 컬렉션을 MAP 한방에 조회
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        //루프를 돌면서 컬렉션 추가
        result.forEach(orderQueryDto -> orderQueryDto.setOrderItems(orderItemMap.get(orderQueryDto.getOrderId())));
        return result;

    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpa_bookshop.repository.order.query.OrderItemQueryDto(o.id,m.name,o.orderDate,o.status,o.address)" +
                        "from OrderItem oi" +
                        "join oi.item i" +
                        "where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));


    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream().map(orderQueryDto -> orderQueryDto.getOrderId()).collect(Collectors.toList());
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery("select new jpabook.jpa_bookshop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate,o.status,d.address,i.name, oi.orderPrice, oi.count)" +
                "from Order o" +
                "join o.member m" +
                "join o.delivery d" +
                "join o.orderItems oi" +
                "join oi.item i", OrderFlatDto.class).getResultList();

    }
}
