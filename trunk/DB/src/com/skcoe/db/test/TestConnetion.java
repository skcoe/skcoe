package com.skcoe.db.test;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.skcoe.db.cfg.HibernateUtil;
import com.skcoe.db.dao.UserAccountHome;
import com.skcoe.db.entity.UserAccount;
import com.skcoe.db.impl.UserAccountImpl;

public abstract class TestConnetion extends HibernateDaoSupport {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*UserAccount usr= new UserAccountHome().findById(1);
		System.out.println(usr);
*/
		
		  System.out.println("Maven + Hibernate + MySQL");
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        
	        session.beginTransaction();
	        new UserAccountImpl().findAll();
	        session.getTransaction().commit();
	        
	      
		//UserAccount u = new UserAccountHome().findById(1);
	}

}
