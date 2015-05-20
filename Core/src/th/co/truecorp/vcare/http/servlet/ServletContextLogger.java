 package th.co.truecorp.vcare.http.servlet;
 
 import java.util.Date;
 import java.util.Properties;
 import javax.servlet.ServletContext;
 import th.co.truecorp.vcare.util.Logger;
 
 public class ServletContextLogger extends Logger
 {
   private ServletContext _sc = null;
 
   public ServletContextLogger(ServletContext sc)
   {
    this._sc = sc;
     this.mLoggerName = this._sc.getServletContextName();
   }
 
   protected void log(int level, String message, Date created)
   {
     this._sc.log(message);
   }
 
   protected void init(Properties envs)
     throws InstantiationException
   {
   }
 }

