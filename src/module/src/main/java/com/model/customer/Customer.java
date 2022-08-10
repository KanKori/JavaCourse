package com.model.customer;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Customer {
    private String id;
    private String email;
    private int age;

    public Customer() {
        id = UUID.randomUUID().toString();
    }
}
