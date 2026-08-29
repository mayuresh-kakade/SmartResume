package com.smartresume.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.JobRole;
import com.smartresume.util.HibernateUtil;

public class JobRoleDAO {

    private final SessionFactory sessionFactory;

    public JobRoleDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    // Save job role
    public boolean saveJobRole(JobRole jobRole) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.persist(jobRole);

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

    // Get all job roles
    public List<JobRole> findAllJobRoles() {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM JobRole ORDER BY title",
                    JobRole.class
            ).getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // Get job roles of a company
    public List<JobRole> findByCompanyId(Long companyId) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM JobRole j WHERE j.company.id = :companyId ORDER BY title",
                    JobRole.class
            )
            .setParameter("companyId", companyId)
            .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // Find by ID
    public JobRole findById(Long id) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(JobRole.class, id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // Update
    public boolean updateJobRole(JobRole jobRole) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.merge(jobRole);

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

    // Delete
    public boolean deleteJobRole(JobRole jobRole) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.remove(
                    session.merge(jobRole)
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