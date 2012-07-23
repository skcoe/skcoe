package com.skcoe.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.skcoe.persistence.HibernateUtil;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Maven + Hibernate + MySQL");
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        /*Stock stock = new Stock();
        stock.setStockCode("4715");
        stock.setStockName("GENM");
        
        session.save(stock);
       */
        Query query = session.createQuery("from UserAccount");
        
        List list = query.list();
        
        for (Object object : list) {
			System.out.println(object);
		}
        //session.delete(stock);
        
        session.getTransaction().commit();
        
        
    }
}
