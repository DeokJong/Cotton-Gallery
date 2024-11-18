package com.cottongallery.backend.order.domain;

import com.cottongallery.backend.auth.domain.Account;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private AddressType addressType;

    public Address(String zipcode, String street, String detail, AddressType addressType) {
        this.zipcode = zipcode;
        this.street = street;
        this.detail = detail;
        this.addressType = addressType;
    }
}
