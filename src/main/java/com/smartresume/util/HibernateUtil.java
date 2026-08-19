package com.smartresume.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.smartresume.entity.Education;
import com.smartresume.entity.Experience;
import com.smartresume.entity.Project;
import com.smartresume.entity.Resume;
import com.smartresume.entity.Skill;
import com.smartresume.entity.User;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {

            Configuration configuration = new Configuration();

            configuration.configure("hibernate.cfg.xml");

            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Resume.class);
            configuration.addAnnotatedClass(Education.class);
            configuration.addAnnotatedClass(Experience.class);
            configuration.addAnnotatedClass(Skill.class);
            configuration.addAnnotatedClass(Project.class);

            sessionFactory = configuration.buildSessionFactory();

            System.out.println(
                    "Hibernate SessionFactory created."
            );

        } catch (Throwable e) {

            System.out.println(
                    "Hibernate SessionFactory creation failed."
            );

            e.printStackTrace();

            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}