package jpabook.jpa_bookshop.domain;

import jakarta.persistence.*;
import jpabook.jpa_bookshop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    private String name;

    /*다대다: db에서 성립되지 않음. 중간테이블 하나 생성해야함*/
    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "CATEGORY_ITEM",
                    joinColumns = @JoinColumn(name = "category_id"),
                    inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    /*동일한 엔티티에서 서로 연관관계 맺는 방법*/
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //관계 편의 메서드
    public void setChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

}
