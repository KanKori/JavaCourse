package com.service.annotation;

import com.annotations.Autowired;
import com.annotations.Singleton;
import com.repository.product.laptop.LaptopRepositoryI;
import com.repository.product.phone.PhoneRepositoryI;
import com.repository.product.tablet.TabletRepositoryI;
import com.service.product.laptop.LaptopServiceAbstract;
import com.service.product.phone.PhoneServiceAbstract;
import com.service.product.tablet.TabletServiceAbstract;
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
                if (declaredConstructor.isAnnotationPresent(Autowired.class)) {
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
                if (declaredConstructor.isAnnotationPresent(Autowired.class)) {
                    try {
                        if (singletonClass.equals(LaptopServiceAbstract.class)) {
                            Object constructorOfClass = declaredConstructor.newInstance(singletonCache.get(LaptopRepositoryI.class));
                            singletonCache.put(singletonClass, constructorOfClass);
                        } else {
                            if (singletonClass.equals(PhoneServiceAbstract.class)) {
                                Object constructorOfClass = declaredConstructor.newInstance(singletonCache.get(PhoneRepositoryI.class));
                                singletonCache.put(singletonClass, constructorOfClass);
                            } else {
                                if (singletonClass.equals(TabletServiceAbstract.class)) {
                                    Object constructorOfClass = declaredConstructor.newInstance(singletonCache.get(TabletRepositoryI.class));
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
