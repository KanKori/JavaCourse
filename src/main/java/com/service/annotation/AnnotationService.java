package com.service.annotation;

import com.annotations.Autowired;
import com.annotations.Singleton;
import com.repository.product.hibernate.laptop.LaptopRepositoryHibernate;
import com.repository.product.hibernate.phone.PhoneRepositoryHibernate;
import com.repository.product.hibernate.tablet.TabletRepositoryHibernate;
import com.repository.product.laptop.LaptopRepository;
import com.repository.product.phone.PhoneRepository;
import com.repository.product.tablet.TabletRepository;
import com.service.product.laptop.LaptopService;
import com.service.product.phone.PhoneService;
import com.service.product.tablet.TabletService;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationService.class);
    Map<Class<?>, Object> singletonCache = new HashMap<>();

    public Map<Class<?>, Object> getSingletonCache() {
        Reflections reflections = new Reflections("com");
        Set<Class<?>> singletonClasses = reflections.getTypesAnnotatedWith(Singleton.class);
        cacheRepositories(singletonClasses);
        cacheServices(singletonClasses);
        return singletonCache;
    }

    private void cacheRepositories(Set<Class<?>> singletonClasses) {
        singletonClasses.forEach(singletonClass -> {
            Constructor<?>[] declaredConstructors = singletonClass.getDeclaredConstructors();
            for (Constructor<?> declaredConstructor : declaredConstructors) {
                if (declaredConstructor.isAnnotationPresent(Autowired.class) && declaredConstructor.getParameterCount() == 0) {
                    try {
                        Object constructorOfSingletonClass = singletonClass.getDeclaredConstructor();
                        singletonCache.put(singletonClass, constructorOfSingletonClass);
                    } catch (NoSuchMethodException e) {
                        LOGGER.error(String.valueOf(e));
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void cacheServices(Set<Class<?>> singletonClasses) {
        singletonClasses.forEach(singletonClass -> {
            for (Constructor<?> declaredConstructor : singletonClass.getDeclaredConstructors()) {
                if (declaredConstructor.isAnnotationPresent(Autowired.class) && declaredConstructor.getParameterCount() == 1) {
                    try {
                        if (singletonClass.equals(LaptopService.class)) {
                            Object constructorOfClass = declaredConstructor.newInstance(singletonCache.get(LaptopRepositoryHibernate.class));
                            singletonCache.put(singletonClass, constructorOfClass);
                        } else {
                            if (singletonClass.equals(PhoneService.class)) {
                                Object constructorOfClass = declaredConstructor.newInstance(singletonCache.get(PhoneRepositoryHibernate.class));
                                singletonCache.put(singletonClass, constructorOfClass);
                            } else {
                                if (singletonClass.equals(TabletService.class)) {
                                    Object constructorOfClass = declaredConstructor.newInstance(singletonCache.get(TabletRepositoryHibernate.class));
                                    singletonCache.put(singletonClass, constructorOfClass);
                                }
                            }
                        }
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                        LOGGER.error(String.valueOf(e));
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
