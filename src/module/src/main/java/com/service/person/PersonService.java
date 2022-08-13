package com.service.person;

import com.model.customer.Customer;
import com.service.person.interfaces.ICustomerService;

import java.util.Random;

public class PersonService implements ICustomerService<Customer> {
    private static final Random RANDOM = new Random();
    final static int MINIMAL_EMAIL_LENGTH = 10;

    public Customer createRandomCustomer() {
        Customer customer = new Customer();
        customer.setAge(RANDOM.nextInt(14, 105));
        customer.setEmail(generateRandomEmail());
        return customer;
    }

    private static String generateRandomEmail() {
        StringBuilder emailAddress = new StringBuilder();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        while (emailAddress.length() < MINIMAL_EMAIL_LENGTH) {
            int index = RANDOM.nextInt(alphabet.length());
            emailAddress.append(alphabet.charAt(index));
        }
        emailAddress.append("@favemaildomen.com");
        return emailAddress.toString();
    }
}
