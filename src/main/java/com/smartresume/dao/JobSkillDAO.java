package com.smartresume.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.JobSkill;
import com.smartresume.util.HibernateUtil;

public class JobSkillDAO {

    private final SessionFactory sessionFactory;

    public JobSkillDAO() {
        sessionFactory =
                HibernateUtil.getSessionFactory();
    }

    // Save job skill
    public boolean saveJobSkill(JobSkill jobSkill) {

        Transaction transaction = null;

        try (Session session =
                     sessionFactory.openSession()) {

            transaction =
                    session.beginTransaction();

            session.persist(jobSkill);

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

    // Get all skills for a job role
    public List<JobSkill> findByJobRoleId(
            Long jobRoleId) {

        try (Session session =
                     sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM JobSkill js "
                    + "WHERE js.jobRole.id = :jobRoleId "
                    + "ORDER BY js.id",
                    JobSkill.class
            )
            .setParameter(
                    "jobRoleId",
                    jobRoleId
            )
            .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // Find skill by ID
    public JobSkill findById(Long id) {

        try (Session session =
                     sessionFactory.openSession()) {

            return session.get(
                    JobSkill.class,
                    id
            );

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // Delete job skill
    public boolean deleteJobSkill(
            JobSkill jobSkill) {

        Transaction transaction = null;

        try (Session session =
                     sessionFactory.openSession()) {

            transaction =
                    session.beginTransaction();

            session.remove(
                    session.merge(jobSkill)
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