package jpabook.jpa_bookshop.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public enum DeliveryStatus {
    READY, COMP
}
