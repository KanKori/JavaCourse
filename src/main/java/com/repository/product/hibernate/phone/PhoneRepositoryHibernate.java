package com.repository.product.hibernate.phone;

import com.annotations.Autowired;
import com.annotations.Singleton;
import com.config.HibernateSessionFactoryUtil;
import com.model.product.phone.Phone;
import com.repository.invoice.hibernate.InvoiceRepositoryHibernate;
import com.repository.product.phone.PhoneRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Singleton
public class PhoneRepositoryHibernate extends PhoneRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepositoryHibernate.class);
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private static PhoneRepositoryHibernate instance;

    @Autowired
    public PhoneRepositoryHibernate() {
    }

    public static PhoneRepositoryHibernate getInstance() {
        if (instance == null) {
            instance = new PhoneRepositoryHibernate();
        }
        return instance;
    }

    @Override
    public void save(Phone phone) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(phone);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Phone> phones) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Phone phone : phones) {
            session.save(phone);
        }
        session.getTransaction().commit();
        session.close();
    }

    public List<Phone> findAll() {
        Session session = sessionFactory.openSession();
        List<Phone> phones = session.createQuery("select phone from Phone phone", Phone.class).getResultList();
        session.close();
        return phones;
    }

    @Override
    public Optional<Phone> findById(String id) {
        Session session = sessionFactory.openSession();
        Optional<Phone> phone = Optional.ofNullable(session.find(Phone.class, id));
        session.close();
        return phone;
    }

    @Override
    public boolean update(Phone phone) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(phone);
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
            session.remove(findById(id).orElse(null));
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
            return false;
        }
    }
}
