 package th.co.truecorp.vcare.http.servlet;
 
 import java.util.Enumeration;
 import java.util.Properties;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletContext;
 
 public class AppServletConfig
   implements ServletConfig
 {
   private String _path = null;
   private String _className = null;
   private Properties _initParams = null;
   private ServletContext _servletContext = null;
 
   AppServletConfig(ServletContext context, String path, String class_name, Properties init_params)
   {
     this._servletContext = context;
     if (!path.startsWith("/"))
       this._path = ("/" + path);
     else {
       this._path = path;
     }
     this._className = class_name;
     this._initParams = init_params;
   }
 
   public String getInitParameter(String name)
   {
     return this._initParams.getProperty(name);
   }
 
   public Enumeration getInitParameterNames()
   {
     return this._initParams.propertyNames();
   }
 
   public ServletContext getServletContext()
   {
     return this._servletContext;
   }
 
   public String getServletName()
   {
     return this._path;
   }
 
   public String getServletClassName()
   {
     return this._className;
   }
 }

