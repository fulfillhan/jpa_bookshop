package jpabook.jpa_bookshop.domain.item;

import jakarta.persistence.*;
import jpabook.jpa_bookshop.domain.Category;
import jpabook.jpa_bookshop.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
* 엔티티에 GETTER는 열어두되, SETTER 필요한 경우에만 사용한다!
* */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//단일 테이블 전략
@DiscriminatorColumn //하위 테이블의 구분 컬럼 생성(default=DTYPE)
public class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
