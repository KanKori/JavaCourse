package com.repository.product.hibernate.laptop;

import com.annotations.Autowired;
import com.annotations.Singleton;
import com.config.HibernateSessionFactoryUtil;
import com.model.product.laptop.Laptop;
import com.repository.invoice.database.InvoiceRepositoryDB;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Singleton
public class LaptopRepositoryHibernate implements ILaptopRepositoryHibernate {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepositoryDB.class);
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private static LaptopRepositoryHibernate instance;

    @Autowired
    public LaptopRepositoryHibernate() {
    }

    public static LaptopRepositoryHibernate getInstance() {
        if (instance == null) {
            instance = new LaptopRepositoryHibernate();
        }
        return instance;
    }

    @Override
    public void save(Laptop laptop) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(laptop);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Laptop> phones) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Laptop laptop : phones) {
            session.save(laptop);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Laptop> findAll() {
        Session session = sessionFactory.openSession();
        List<Laptop> phones = session.createQuery("select laptop from Laptop laptop", Laptop.class).getResultList();
        session.close();
        return phones;
    }

    @Override
    public Optional<Laptop> findById(String id) {
        Session session = sessionFactory.openSession();
        Optional<Laptop> laptop = Optional.ofNullable(session.find(Laptop.class, id));
        session.close();
        return laptop;
    }

    @Override
    public boolean update(Laptop laptop) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(laptop);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(findById(id).get());
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
            return false;
        }
    }
}
