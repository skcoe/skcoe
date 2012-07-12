package com.skcoe.db.cfg;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	//Configuration cfg = new Configuration().configure("/hibernate.cfg.xml");
        	
    		//File file = new File("../cfg/hibernate.cfg.xml");
        	SessionFactory sessionFactory = new Configuration()
            .configure("/com/skcoe/db/cfg/hibernate.cfg.xml")
            .buildSessionFactory();
           // return new AnnotationConfiguration().configure().buildSessionFactory();
        	return sessionFactory ;
            
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }

}