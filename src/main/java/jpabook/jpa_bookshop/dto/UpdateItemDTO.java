package jpabook.jpa_bookshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateItemDTO {

    private String name;
    private int price;
    private int stockQuantity;
}
