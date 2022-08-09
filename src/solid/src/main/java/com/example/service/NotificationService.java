package com.example.service;

import com.example.model.NotifiableProduct;
import com.example.repository.ProductRepository;

import java.util.List;

public class NotificationService implements INotificationService<NotifiableProduct> {
    private final ProductRepository repository = ProductRepository.getInstance();

    public int sendNotifications(List<NotifiableProduct> products) {
        int notifications = 0;
        for (NotifiableProduct product : products) {
            //sending some notifications here
            notifications++;
        }
        return notifications;
    }

    public List<NotifiableProduct> filterNotifiableProduct() {
        return repository.getAll()
                .stream()
                .filter(NotifiableProduct.class::isInstance)
                .map(NotifiableProduct.class::cast)
                .toList();
    }

    public int filterNotifiableProductsAndSendNotifications() {
        return sendNotifications(filterNotifiableProduct());
    }
}
