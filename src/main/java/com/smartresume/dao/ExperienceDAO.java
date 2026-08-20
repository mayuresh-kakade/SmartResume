package com.smartresume.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.Experience;
import com.smartresume.util.HibernateUtil;

public class ExperienceDAO {

    private final SessionFactory sessionFactory;

    public ExperienceDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    // Save experience
    public boolean saveExperience(Experience experience) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.persist(experience);

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

    // Get experiences for a resume
    public List<Experience> findByResumeId(Long resumeId) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM Experience e WHERE e.resume.id = :resumeId ORDER BY e.id",
                    Experience.class
            )
            .setParameter("resumeId", resumeId)
            .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // Find experience by ID
    public Experience findById(Long id) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(Experience.class, id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // Update experience
    public boolean updateExperience(Experience experience) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.merge(experience);

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

    // Delete experience
    public boolean deleteExperience(Experience experience) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.remove(
                    session.merge(experience)
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