package jpabook.jpa_bookshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    //단일 테이블 전략부터
}
