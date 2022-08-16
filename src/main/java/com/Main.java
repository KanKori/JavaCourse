package com;

import java.io.InputStream;

import com.model.product.phone.Phone;
import com.service.parser.FileParser;
import com.service.product.phone.PhoneService;

public class Main {

    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStreamXML = classLoader.getResourceAsStream("phone.xml");
        InputStream inputStreamJSON = classLoader.getResourceAsStream("phone.json");

        Phone phoneXML = PhoneService.createPhoneFromMap(new FileParser().parseXMLToMap(inputStreamXML));
        System.out.println("XML: " + phoneXML);
        System.out.println("XML Phone OS: " + phoneXML.getOS().getDesignation() + " " + phoneXML.getOS().getVersion());

        Phone phoneJSON = PhoneService.createPhoneFromMap(new FileParser().parseJSONToMap(inputStreamJSON));
        System.out.println("JSON: " + phoneJSON);
        System.out.println("JSON Phone OS: " + phoneJSON.getOS().getDesignation() + " " + phoneJSON.getOS().getVersion());
    }
}