package com.skcoe.common.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.skcoe.persistence.HibernateUtil;

public class UserAccountService 
{
    public static void queryAll()
    {
        System.out.println("Maven + Hibernate + MySQL");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
       
        Query query = session.createQuery("from UserAccount");
        List list = query.list();
        for (Object object : list) {
			System.out.println(object);
		}
        //session.delete(stock);
        
        session.getTransaction().commit();
        
        
    }
}
