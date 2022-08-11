package com.service;

import com.model.customer.Customer;

public interface ICustomerService<T extends Customer> {
    T createRandomCustomer();
}
