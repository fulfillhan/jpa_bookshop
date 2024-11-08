package jpabook.jpa_bookshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;
/*
* 값 타입을 정의하는 곳에 사용
* 기본 생성자 필수!
* */

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String code;

    /*기본 생성자는 public, protected 를 하는 것이 좋다.
    * why: JPA 구현체인 hibernate 에서 제공하는 다양한 기능을 활용하기 위해서, 또한
    * 안전성 측면에서 protected 를 사용하는 것이 좋다. */
    protected Address() {
    }

    public Address(String city, String street, String code) {
        this.city = city;
        this.street = street;
        this.code = code;
    }

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
