package com.repository.product.hibernate.phone;

import com.model.product.phone.Phone;

import java.util.List;
import java.util.Optional;

public interface IPhoneRepositoryHibernate {
    void save(Phone phone);

    void saveAll(List<Phone> phones);

    List<Phone> findAll();

    Optional<Phone> findById(String id);

    boolean update(Phone phone);

    boolean delete(String id);
}
