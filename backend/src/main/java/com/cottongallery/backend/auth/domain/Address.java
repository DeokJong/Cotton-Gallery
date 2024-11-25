package com.cottongallery.backend.auth.domain;

import com.cottongallery.backend.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {

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

    private Address(String zipcode, String street, String detail, AddressType addressType) {
        this.zipcode = zipcode;
        this.street = street;
        this.detail = detail;
        this.addressType = addressType;
    }

    public static Address createAddressWithoutAccount(String zipcode, String street, String detail, AddressType addressType) {
        return new Address(zipcode, street, detail, addressType);
    }

    public static Address createAddress(String zipcode, String street, String detail, AddressType addressType, Account account) {
        Address address = new Address(zipcode, street, detail, addressType);
        address.assignAccount(account);

        return address;
    }

    public void assignAccount(Account account) {
        this.account = account;
    }

    public void changeAddressType(AddressType addressType) {
        this.addressType = addressType;
    }
}
