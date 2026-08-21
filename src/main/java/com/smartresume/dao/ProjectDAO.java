package com.smartresume.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.Project;
import com.smartresume.util.HibernateUtil;

public class ProjectDAO {

    private final SessionFactory sessionFactory;

    public ProjectDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    // Save project
    public boolean saveProject(Project project) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.persist(project);

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

    // Get all projects for a resume
    public List<Project> findByResumeId(Long resumeId) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM Project p WHERE p.resume.id = :resumeId ORDER BY p.id",
                    Project.class
            )
            .setParameter("resumeId", resumeId)
            .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // Find project by ID
    public Project findById(Long id) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(Project.class, id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // Update project
    public boolean updateProject(Project project) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.merge(project);

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

    // Delete project
    public boolean deleteProject(Project project) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.remove(
                    session.merge(project)
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