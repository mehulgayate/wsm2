package com.evalua.entity.support;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DataStoreManager {

	private SessionFactory sessionFactory;	

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session save(EntityBase entity){		
		Session session=getSession();
		session.saveOrUpdate(entity);
		return session;
	}
	
	public Session delete(EntityBase entity){		
		Session session=getSession();
		session.delete(entity);
		return session;
	}

	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

}
