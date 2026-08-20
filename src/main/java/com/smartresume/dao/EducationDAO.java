package com.smartresume.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.Education;
import com.smartresume.util.HibernateUtil;

public class EducationDAO {

    private final SessionFactory sessionFactory;

    public EducationDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    // Save Education
    public boolean saveEducation(Education education) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.persist(education);

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

    // Get all education records for a resume
    public List<Education> findByResumeId(Long resumeId) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM Education e WHERE e.resume.id = :resumeId ORDER BY e.id",
                    Education.class
            )
            .setParameter("resumeId", resumeId)
            .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // Find Education by ID
    public Education findById(Long id) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(Education.class, id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // Update Education
    public boolean updateEducation(Education education) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.merge(education);

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

    // Delete Education
    public boolean deleteEducation(Education education) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.remove(
                    session.merge(education)
            );

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
}