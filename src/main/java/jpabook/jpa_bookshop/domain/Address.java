package jpabook.jpa_bookshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
/*
* 값 타입을 정의하는 곳에 사용
* 기본 생성자 필수!
* */

@Embeddable
@NoArgsConstructor
@Getter
public class Address {
    private String city;
    private String street;
    private String code;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getCode(), address.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getCode());
    }
}
