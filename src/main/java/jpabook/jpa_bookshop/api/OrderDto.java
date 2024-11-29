package jpabook.jpa_bookshop.api;

import jpabook.jpa_bookshop.domain.Address;
import jpabook.jpa_bookshop.domain.Order;
import jpabook.jpa_bookshop.domain.OrderItem;
import jpabook.jpa_bookshop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Data
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;
    private List<OrderItemDto> orderItems; //OrderItem 조차도 dto로 변경해야 된다.

    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        status = order.getStatus();
        address = order.getDelivery().getAddress();
        orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(toList());
    }

}
