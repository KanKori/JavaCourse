package com.service.person;

import com.model.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
    }

    @Test
    void createRandomCustomer() {
        Customer customer = personService.createRandomCustomer();
        assertNotNull(customer);
    }
}