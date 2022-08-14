package com.service.person.interfaces;

import com.model.customer.Customer;

public interface ICustomerService<T extends Customer> {
    T createRandomCustomer();
}
