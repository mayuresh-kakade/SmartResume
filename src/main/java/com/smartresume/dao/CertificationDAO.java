package com.smartresume.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.Certification;
import com.smartresume.util.HibernateUtil;

public class CertificationDAO {

    private final SessionFactory sessionFactory;

    public CertificationDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    // Save certification
    public boolean saveCertification(Certification certification) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.persist(certification);

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

    // Get all certifications for a resume
    public List<Certification> findByResumeId(Long resumeId) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM Certification c WHERE c.resume.id = :resumeId ORDER BY c.id",
                    Certification.class
            )
            .setParameter("resumeId", resumeId)
            .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // Find certification by ID
    public Certification findById(Long id) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(Certification.class, id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // Update certification
    public boolean updateCertification(
            Certification certification) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.merge(certification);

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

    // Delete certification
    public boolean deleteCertification(
            Certification certification) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.remove(
                    session.merge(certification)
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