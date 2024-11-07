package jpabook.jpa_bookshop.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member member;

    @Enumerated
    private OrderStatus status;

    @OneToOne
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    private LocalDateTime orderDate;

    private int orderPrice;

    private int count;


}
