package com.smartresume.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.Skill;
import com.smartresume.util.HibernateUtil;

public class SkillDAO {

    private final SessionFactory sessionFactory;

    public SkillDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    // Save skill
    public boolean saveSkill(Skill skill) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.persist(skill);

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

    // Get all skills for a resume
    public List<Skill> findByResumeId(Long resumeId) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(
                    "FROM Skill s WHERE s.resume.id = :resumeId ORDER BY s.id",
                    Skill.class
            )
            .setParameter("resumeId", resumeId)
            .getResultList();

        } catch (Exception e) {

            e.printStackTrace();

            return List.of();
        }
    }

    // Find skill by ID
    public Skill findById(Long id) {

        try (Session session = sessionFactory.openSession()) {

            return session.get(Skill.class, id);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // Update skill
    public boolean updateSkill(Skill skill) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.merge(skill);

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

    // Delete skill
    public boolean deleteSkill(Skill skill) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            session.remove(
                    session.merge(skill)
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