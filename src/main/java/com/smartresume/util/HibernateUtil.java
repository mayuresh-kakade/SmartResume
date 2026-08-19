package com.smartresume.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.smartresume.entity.User;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

        } catch (Throwable e) {
            System.err.println("Hibernate SessionFactory creation failed.");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}