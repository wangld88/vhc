package com.vhc.service.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vhc.exception.DataAccessException;


@Component
public class HibernateUtil {

	@Autowired
	private SessionFactory sessionFactory;

	
	public Session openSession(){
		Session session = this.sessionFactory.getCurrentSession();
		return session;
	}
	
	public void closeSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ignored) {
            	throw new DataAccessException(ignored);
            }
        }
    }

}
