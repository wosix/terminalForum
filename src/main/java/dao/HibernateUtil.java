package dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class HibernateUtil<T> {

    //private final EntityManager entityManager;
    private final Session session;
    private final Class clazz;


    public HibernateUtil(EntityManager entityManager, Class clazz) {
        //this.entityManager = entityManager;
        this.session = entityManager.unwrap(Session.class);
        this.clazz = clazz;
    }

    public List<Object> findAll() {
        return session.createQuery("from " + clazz.getName(), clazz).getResultList();
    }

    public Object findById(Long id) {
        Query query = session.createNamedQuery("findById", clazz);
        query.setParameter(1, id);
        return query.getSingleResult();
    }

    public Object findByLogin(String login) {
        Query query = session.createNamedQuery("findByLogin", clazz);
        query.setParameter(2, login);
        return query.getSingleResult();
    }



    public void printAll() {
        System.out.println("Dane z tabeli: ");
        List<Object> entity = findAll();
        for (Object obj : entity) {
            System.out.println(obj);
        }
    }

    public void create(Object objectToCreate) {
        EntityTransaction transaction = null;
        try {
            transaction = session.getTransaction();
            session.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            session.persist(objectToCreate);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = null;
        try {
            transaction = session.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            session.remove(session.find(clazz, id));
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void close() {
        session.close();
    }
}
