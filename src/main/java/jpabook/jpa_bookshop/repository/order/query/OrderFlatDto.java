package jpabook.jpa_bookshop.repository.order.query;

import jpabook.jpa_bookshop.domain.Address;
import jpabook.jpa_bookshop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/*
* sql 을 한번에 갖고 올수 있도록한다.
*
* */
@Data
public class OrderFlatDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    private String itemName;//상품 명
    private int orderPrice; //주문 가격
    private int count;

    public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus status, Address address,
                        List<OrderItemQueryDto> orderItems, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.status = status;
        this.address = address;
        this.orderItems = orderItems;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
