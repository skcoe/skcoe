 package com.skcoe18.core.http.servlet;
 
 import javax.servlet.ServletContext;
 import javax.servlet.ServletContextEvent;
 import javax.servlet.ServletContextListener;
 import org.apache.axis.utils.ClassUtils;
 
 public class ApplicationContextListener
   implements ServletContextListener, AppConfigChangeListener
 {
   public void contextInitialized(ServletContextEvent sce)
   {
     ServletContext sc = sce.getServletContext();
     ApplicationContext app_ctx = new ApplicationContext(sc);
     ClassUtils.setDefaultClassLoader(app_ctx.getClassLoader());
     app_ctx.addAppConfigChangeListener(this);
     sc.setAttribute(ApplicationContext.APPLICATION_CONTEXT_ATTR, app_ctx);
   }
 
   public void contextDestroyed(ServletContextEvent sce)
   {
     ServletContext sc = sce.getServletContext();
     ApplicationContext ctx = ApplicationContext.getContext(sc);
     if (ctx != null)
       ctx.destroy();
     sc.log("Application, " + sc.getServletContextName() + ", closes successfully.");
   }
 
   public void appConfigChanged(ApplicationContext ctx)
   {
     ClassLoader cl = ctx.getClassLoader();
     ClassUtils.setDefaultClassLoader(cl);
     ServletContext sc = ctx.getServletContext();
     sc.log("Application, " + sc.getServletContextName() + ", applcation configuration is changed.");
   }
 }

