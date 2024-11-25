package com.cottongallery.backend.util;

import com.cottongallery.backend.auth.domain.Account;
import com.cottongallery.backend.auth.dto.account.request.AccountCreateRequest;
import com.cottongallery.backend.common.constants.Role;
import com.cottongallery.backend.auth.domain.Address;
import com.cottongallery.backend.auth.domain.AddressType;

public final class AccountTestData {
    public static final Long ID = 1L;
    public static final String NAME = "test";
    public static final String USERNAME = "testtest";
    public static final String PASSWORD = "testtest";
    public static final String EMAIL = "test@test";
    public static final String PHONE_NUMBER = "12345678901";
    public static final String ZIPCODE = "11111";
    public static final String STREET = "testStreet";
    public static final String DETAIL = "testDetail";

    private AccountTestData() {
    }

    public static AccountCreateRequest createTestAccountCreateRequest() {
        return new AccountCreateRequest(
                NAME,
                USERNAME,
                PASSWORD,
                PASSWORD, // confirmPassword
                EMAIL,
                PHONE_NUMBER,
                ZIPCODE,
                STREET,
                DETAIL);
    }

    public static Account createTestAccount() {
        Account account = Account.createAccount(NAME, USERNAME, PASSWORD, EMAIL, PHONE_NUMBER, Role.ROLE_USER);
        account.addAddress(Address.createAddressWithoutAccount(ZIPCODE, STREET, DETAIL, AddressType.PRIMARY));

        return account;
    }

    public static Account createTestAccountWithId() {
        Account account = Account.createAccountWithId(ID, NAME, USERNAME, PASSWORD, EMAIL, PHONE_NUMBER, Role.ROLE_ADMIN);
        account.addAddress(Address.createAddressWithoutAccount(ZIPCODE, STREET, DETAIL, AddressType.PRIMARY));

        return account;
    }
}
