package jpabook.jpa_bookshop.domain.item;

import jakarta.persistence.*;
import jpabook.jpa_bookshop.domain.Category;
import jpabook.jpa_bookshop.domain.OrderItem;
import jpabook.jpa_bookshop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
* 엔티티에 GETTER는 열어두되, SETTER 필요한 경우에만 사용한다!
* */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//단일 테이블 전략
@DiscriminatorColumn //하위 테이블의 구분 컬럼 생성(default=DTYPE)
@Getter
@Setter
public abstract class Item {

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

    //비즈니스 로직(엔티티 내에 비즈니스 로직이 있는 것 좋다.-> 응집력 높이고 객체 지향적)
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock!");
        }
        this.stockQuantity = restStock;
    }
}
