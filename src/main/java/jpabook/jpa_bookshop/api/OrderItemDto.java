package jpabook.jpa_bookshop.api;

import jpabook.jpa_bookshop.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {

    private String itemName; //상품 명
    private int price; //상품 가격
    private int count; //상품 수량

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        price = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }

}
