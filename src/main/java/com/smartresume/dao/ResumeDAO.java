package com.smartresume.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.smartresume.entity.Resume;
import com.smartresume.util.HibernateUtil;

public class ResumeDAO {
	private final SessionFactory sessionFactory;
	public ResumeDAO() {
		sessionFactory=HibernateUtil.getSessionFactory();
	}
	public boolean saveResume(Resume resume) {
		Transaction transaction=null;
		try(Session session=sessionFactory.openSession()){
			transaction=session.beginTransaction();
			session.persist(resume);
			transaction.commit();
			return true;
		}catch(Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
		
	}
	public Resume findResumeByUserId(int userId) {
		try(Session session=sessionFactory.openSession()){
			return session.createQuery(
					"FROM Resume r WHERE r.user.id=:userId",
					Resume.class
				)
				.setParameter("userId",userId)
				.uniqueResult();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateResume(Resume resume) {
		Transaction transaction=null;
		try(Session session=sessionFactory.openSession()){
			transaction=session.beginTransaction();
			session.merge(resume);
			transaction.commit();
			return true;
		}catch(Exception e) {
			if(transaction !=null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}
	public boolean deleteResume(Resume resume) {
		Transaction transaction=null;
		try(Session session=sessionFactory.openSession()){
			transaction=session.beginTransaction();
			session.remove(
					session.merge(resume));
			transaction.commit();
			return true;
		}catch(Exception e) {
			if(transaction !=null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

}
