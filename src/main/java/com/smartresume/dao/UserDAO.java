package com.smartresume.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.User;
import com.smartresume.util.HibernateUtil;

public class UserDAO {

    private final SessionFactory sessionFactory;

    public UserDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public boolean saveUser(User user) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();

            return true;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            return false;
        }
    }

    public User findUserByEmail(String email) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM User u WHERE u.email = :email",
                    User.class
            )
            .setParameter("email", email)
            .uniqueResult();

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public boolean emailExists(String email) {

        try (Session session = sessionFactory.openSession()) {

            Long count = session.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.email = :email",
                    Long.class
            )
            .setParameter("email", email)
            .uniqueResult();

            return count != null && count > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
    public User findUserById(int userId) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(
                    User.class,
                    userId
            );

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}