package com.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

public class HibernateConfig {
    private static SessionFactory sessionFactory;
    private Session session;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure(new File("src/main/resources/hibernate.cfg.xml"))
                    .build();
            try {
                sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(registry);
                throw e;
            }
        }
        return sessionFactory;
    }

    public Session getSession() {
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}