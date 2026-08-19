package com.smartresume.test;

import org.hibernate.SessionFactory;

import com.smartresume.util.HibernateUtil;

public class ResumeHibernateTest {

    public static void main(String[] args) {

        try {

            System.out.println("Starting Hibernate...");

            SessionFactory factory =
                    HibernateUtil.getSessionFactory();

            System.out.println(
                    "Hibernate started successfully."
            );

            factory.close();

            System.out.println(
                    "Hibernate test completed."
            );

        } catch (Exception e) {

            System.out.println(
                    "Hibernate test failed."
            );

            e.printStackTrace();
        }
    }
}