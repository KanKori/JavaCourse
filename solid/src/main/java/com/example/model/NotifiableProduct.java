package com.example.model;

import lombok.Setter;

@Setter
public class NotifiableProduct extends Product implements INotifiableProduct {
    protected String channel;

    @Override
    public String generateAddressForNotification() {
        return "somerandommail@gmail.com";
    }

    @Override
    public String toString() {
        return "NotifiableProduct{" +
                "channel='" + channel + '\'' +
                ", id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}