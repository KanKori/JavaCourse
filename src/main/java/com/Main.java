package com;

import com.service.annotation.AnnotationService;

public class Main {

    public static void main(String[] args) {
        System.out.println(new AnnotationService().getSingletonCache());
    }
}