package com.repository.product.hibernate.tablet;

import com.annotations.Autowired;
import com.annotations.Singleton;
import com.config.HibernateSessionFactoryUtil;
import com.model.product.tablet.Tablet;
import com.repository.invoice.database.InvoiceRepositoryDB;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Singleton
public class TabletRepositoryHibernate implements ITabletRepositoryHibernate {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceRepositoryDB.class);
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    private static TabletRepositoryHibernate instance;

    @Autowired
    public TabletRepositoryHibernate() {
    }

    public static TabletRepositoryHibernate getInstance() {
        if (instance == null) {
            instance = new TabletRepositoryHibernate();
        }
        return instance;
    }

    @Override
    public void save(Tablet tablet) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(tablet);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Tablet> tablets) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Tablet tablet : tablets) {
            session.save(tablet);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Tablet> findAll() {
        Session session = sessionFactory.openSession();
        List<Tablet> tablets = session.createQuery("select tablet from Tablet tablet", Tablet.class).getResultList();
        session.close();
        return tablets;
    }

    @Override
    public Optional<Tablet> findById(String id) {
        Session session = sessionFactory.openSession();
        Optional<Tablet> tablet = Optional.ofNullable(session.find(Tablet.class, id));
        session.close();
        return tablet;
    }

    @Override
    public boolean update(Tablet tablet) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(tablet);
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
