package jpabook.jpa_bookshop.repository;

import jpabook.jpa_bookshop.domain.Address;
import jpabook.jpa_bookshop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderSimpleDto {
        private Long id;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleDto(Long id, String name, LocalDateTime orderDate, OrderStatus status,Address address){
            this.id= id;
            this.name = name;
            this.orderDate = orderDate;
            this.orderStatus = status;
            this.address = address;
        }
}
