package dao;

import com.sun.jdi.ObjectCollectedException;
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

    public Object findPostById(Long id) {
        Query query = session.createNamedQuery("findPostById", clazz);
        query.setParameter(3, id);
        return query.getSingleResult();
    }

    public List<Object> findAllComments(Long postId) {
        return session.createQuery("from Comment where post_id = " + postId, clazz).getResultList();
    }


    public void printAll() {
        int i = 1;
        List<Object> entity = findAll();
        for (Object obj : entity) {
            System.out.println(i++ + " ---------------\n" + obj);
        }
    }

    public void printAllComments(Long postId) {
        int i = 1;
        List<Object> entity = findAllComments(postId);
        System.out.println("\nComments:");
        for (Object obj : entity) {
            System.out.println(i++ + " ---------------\n" + obj);
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
