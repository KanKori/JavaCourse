package com.example.service;

import com.example.model.NotifiableProduct;

import java.util.List;

public interface INotificationService<T extends NotifiableProduct> {
    int sendNotifications(List<T> products);

    List<T> filterNotifiableProduct();
}
