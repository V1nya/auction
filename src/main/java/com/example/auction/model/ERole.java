package com.example.auction.model;

import org.springframework.security.core.GrantedAuthority;

public enum ERole implements GrantedAuthority {
    Salesman, Customer;
//    @Override
//    public String getAuthority() {
//        return name();
//    }

    @Override
    public String getAuthority() {
        return name();
    }
}
