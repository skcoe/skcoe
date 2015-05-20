 package th.co.truecorp.vcare.http.servlet;
 
 import java.io.File;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.w3c.dom.Element;

import th.co.truecorp.vcare.util.FileLogger;
import th.co.truecorp.vcare.util.Logger;
import th.co.truecorp.vcare.util.XmlConfiguration;
 
 public class ApplicationContext
 {
   public static final String EXTERNAL_LIB_PATH_INIT_PARAM = "ExternalLibraryPath";
   public static final String APP_CONFIG_FILENAME_INIT_PARAM = "ApplicationConfigFileName";
   public static final String WEBSERVICE_CONFIG_FILENAME_INIT_PARAM = "WebServiceConfigFileName";
   public static final String APPLICATION_CONTEXT_ATTR = ApplicationContext.class.getName();
 
   private static final ThreadLocal<ApplicationContext> _appThreadLocal = new ThreadLocal<ApplicationContext>();
   private ServletContext _sc;
   private long _lastModified = 0L;
   private ClassLoader _classLoader = null;
   private Map<String, AppServletConfig> _servletConfigMap = new HashMap<String, AppServletConfig>();
   private File _appConfigFile = null;
   private Map<String, Object> _appParameters = new HashMap<String, Object>();
   private Set<AppConfigChangeListener> _appConfigChangeListener = new HashSet<AppConfigChangeListener>();
   private Logger _defaultLogger;
   private String _loggerPath;
   private Map<String, Logger> _loggerMap = new HashMap<String, Logger>();
   private Map<String, Properties> _jdbcMap = new HashMap<String, Properties>();
   private Map<String, JdbcConnectionFactoryInterface> _jdbcFactory = new HashMap<String, JdbcConnectionFactoryInterface>();
 
   private XmlConfiguration _xmlConfig = null;
 
   ApplicationContext(ServletContext sc)
   {
     this._sc = sc;
     this._defaultLogger = new ServletContextLogger(this._sc);
     init();
   }
 
   public static ApplicationContext currentContext()
   {
     return (ApplicationContext)_appThreadLocal.get();
   }
 
   public static ApplicationContext getContext(ServletContext sc) {
     return (ApplicationContext)sc.getAttribute(APPLICATION_CONTEXT_ATTR);
   }
 
   void saveCurrentContext()
   {
     _appThreadLocal.set(this);
   }
   public File getAppConfigFile() {
     return this._appConfigFile;
   }
   public long lastModified() { return this._lastModified; }
 
   public AppServletConfig getServletConfig(String path)
   {
     String search_path = path;
     if (!search_path.startsWith("/")) {
       search_path = "/" + search_path;
     }
     AppServletConfig config = (AppServletConfig)this._servletConfigMap.get(search_path);
 
     while ((config == null) && (search_path.length() > 1))
     {
       int last_pos = search_path.lastIndexOf("/");
       if (last_pos <= 0)
         search_path = "/";
       else {
         search_path = search_path.substring(0, last_pos);
       }
       config = (AppServletConfig)this._servletConfigMap.get(search_path);
     }
     return config;
   }
 
   public String[] getServletClassNames()
   {
     return getServletNames();
   }
 
   public String getServletClassName(String servlet_name)
   {
     AppServletConfig app_config = getServletConfig(servlet_name);
     return app_config.getServletClassName();
   }
 
   public String[] getServletNames()
   {
	   Object[] object_keys = this._servletConfigMap.keySet().toArray();
	   Arrays.sort(object_keys);
	   String[] string_keys = new String[object_keys.length];
	   for (int i = 0; i < object_keys.length; i++)
		   string_keys[i] = ((String)object_keys[i]);
	   return string_keys;
   }
 
   public synchronized void setParameter(String name, Object value)
   {
     this._appParameters.put(name, value);
   }
 
   public synchronized Object getParameter(String name)
   {
     return this._appParameters.get(name);
   }
 
   public synchronized void removeParameter(String name)
   {
     this._appParameters.remove(name);
   }
 
   public String[] getParameterNames()
   {
     Object[] object_keys = this._appParameters.keySet().toArray();
     Arrays.sort(object_keys);
    String[] string_keys = new String[object_keys.length];
     for (int i = 0; i < object_keys.length; i++)
       string_keys[i] = ((String)object_keys[i]);
     return string_keys;
   }
 
   public ClassLoader getClassLoader()
   {
     return this._classLoader;
   }
 
   private void init()
   {
     this._sc.log("###1AAA## Application, " + this._sc.getServletContextName() + ", is starting");
     try
     {
       String ext_lib_path = this._sc.getInitParameter("ExternalLibraryPath");
      this._sc.log("###AAA## ext_lib_path :" + ext_lib_path );
       this._classLoader = new HttpServletClassLoader(getClass().getClassLoader(), ext_lib_path);
       this._loggerPath = ext_lib_path;
 
       String config_filename = this._sc.getInitParameter("ApplicationConfigFileName");
       URL file_url = this._classLoader.getResource(config_filename);
 
      
 
       if (file_url == null) {
         this._sc.log("###AAA## ConfigurationFileName, " + config_filename + ", does not exist.");
       } else {
         this._appConfigFile = new File(new URI(file_url.toString()));
         loadAppConfig();
       }
 
       this._lastModified = (this._appConfigFile == null ? 0L : this._appConfigFile.lastModified());
 
       this._sc.log("Application, " + this._sc.getServletContextName() + ", starts successfully.");
     } catch (Throwable t) {
       this._sc.log(t.getMessage(), t);
     }
   }
 
   private void loadAppConfig() throws Exception {
		String ext_lib_path = this._sc.getInitParameter("ExternalLibraryPath");
		this._xmlConfig = XmlConfiguration.load(this._appConfigFile.getAbsolutePath());

		//load Servlet 
		int servlet_count = this._xmlConfig.getKeyCount("ServletMapping/Servlet");
		this._servletConfigMap.clear();
		for (int i = 0; i < servlet_count; i++) {
			Element servlet_elem = this._xmlConfig.getSectionNode(null, i, "ServletMapping/Servlet");
			Properties att_prop = this._xmlConfig.getValues(servlet_elem, 0, null);
			String servlet_path = att_prop.getProperty("path");
			String servlet_class = att_prop.getProperty("class");

			if ((servlet_path == null) || (servlet_path.length() == 0)) {
				continue;
			}
			if ((servlet_class == null) || (servlet_class.length() == 0)) {
				throw new Exception( "attribute class is required for servlet : " + servlet_path);
			}
			
		
			int param_count = this._xmlConfig.getKeyCount(servlet_elem, "InitParam/Param");
			Properties servlet_params = new Properties();
			for (int j = 0; j < param_count; j++) {
				Properties param_prop = this._xmlConfig.getValues(servlet_elem, j, "InitParam/Param");
				String param_name = param_prop.getProperty("name");
				String param_value = param_prop.getProperty("value");
				if ((param_name != null) && (param_value != null)) {
					servlet_params.setProperty(param_name, param_value);
				}
			}
			this._sc.log("servlet_path:" + servlet_path + "  ,servlet_class:" + servlet_class + " ,servlet_params:" + servlet_params);
			AppServletConfig servlet_config = new AppServletConfig(this._sc, servlet_path, servlet_class, servlet_params);

			this._servletConfigMap.put(servlet_config.getServletName(), servlet_config);
		}
		//load Param	
		int param_count = this._xmlConfig.getKeyCount("Parameter/Param");
		this._appParameters.clear();
		for (int i = 0; i < param_count; i++) {
			Properties att_prop = this._xmlConfig.getValues(i,
					"Parameter/Param");
			String param_name = att_prop.getProperty("name");
			String param_value = att_prop.getProperty("value");
			if ((param_name != null) && (param_value != null)) {
				this._appParameters.put(param_name, param_value);
			}
		}
		
		//load Logger
		Properties logger_prop = this._xmlConfig.getValues("Logger");
		if (logger_prop != null) {
			String default_path_conf = logger_prop.getProperty("defaultpath", ext_lib_path);
			String default_level_conf = logger_prop.getProperty("defaultlevel", "2");

			this._loggerPath = default_path_conf;

			int default_level = 2;
			try {
				default_level = Integer.parseInt(default_level_conf);
			} catch (Exception e) { }
			this._defaultLogger.setLogLevel(default_level);

			if (!default_path_conf.endsWith("/")) {
				default_path_conf = default_path_conf + "/";
			}
			
			int logger_count = this._xmlConfig.getKeyCount("Logger/Log");
			this._loggerMap.clear();
			for (int i = 0; i < logger_count; i++) {
				Properties att_prop = this._xmlConfig.getValues(i, "Logger/Log");
				String logger_name_conf = att_prop.getProperty("name");
				String logger_filename_conf = att_prop.getProperty("filename");
				if ((logger_name_conf != null) && (logger_filename_conf != null)) {
					File logger_file = new File(logger_filename_conf);
					if (!logger_file.isAbsolute()) {
						logger_filename_conf = default_path_conf + logger_filename_conf;
					}
					String logger_encoding_conf = att_prop.getProperty( "encoding");
					String logger_level_conf = att_prop.getProperty("level" );
					int logger_level = 2;
					try {
						logger_level = Integer.parseInt(logger_level_conf);
					} catch (Exception e) { }
					Logger file_logger = null;
					Properties envs = new Properties();
					envs.setProperty("filename", logger_filename_conf);
					envs.setProperty("encoding", logger_encoding_conf);
					try {
						file_logger = new FileLogger(logger_name_conf, logger_level, envs);
					} catch (Exception e) {
					}
					if (file_logger != null)
						this._loggerMap.put(logger_name_conf, file_logger);
				}
			}
		}
		
		//load ConnectionPool
		int jdbc_count = this._xmlConfig.getKeyCount("ConnectionPool/DataSource");
		this._jdbcMap.clear();
		clearJdbcFactory();
		for (int i = 0; i < jdbc_count; i++) {
			Properties att_prop = this._xmlConfig.getValues(i, "ConnectionPool/DataSource");
			String jdbc_name = att_prop.getProperty("name");
			this._jdbcMap.put(jdbc_name, att_prop);
		}
	}
 
   synchronized void reload()
   {
     this._sc.log("### AppContext  reloading...");
     if (this._appConfigFile == null)
     {
       String config_filename = this._sc.getInitParameter("ApplicationConfigFileName");
       /*URL file_url = this._classLoader.getResource(config_filename);
       if (file_url == null) {
         this._sc.log("### ConfigurationFileName, " + config_filename + ", does not exist.");
         return;
       }*/
       try {
         this._appConfigFile = new File(config_filename);
       } catch (Exception e) {
         this._sc.log("Internal Error " + e.getMessage(), e);
         return;
       }
     }
     this._sc.log("compare last modified and _appConfigFile.lastModified()");
 
     if (this._lastModified >= this._appConfigFile.lastModified()) return;
 
     String ext_lib_path = this._sc.getInitParameter("ExternalLibraryPath");
     try {
       loadAppConfig();
       this._classLoader = new HttpServletClassLoader(ApplicationContextListener.class.getClassLoader(), ext_lib_path);
       this._lastModified = this._appConfigFile.lastModified();
       fireAppConfigChangeEvent();
     } catch (Throwable t) {
       this._sc.log(t.getMessage(), t);
     }
   }
 
   void destroy()
   {
     clearJdbcFactory();
   }
 
   public synchronized void addAppConfigChangeListener(AppConfigChangeListener listener)
   {
     this._appConfigChangeListener.add(listener);
   }
 
   public synchronized void removeAppConfigChangeListener(AppConfigChangeListener listener)
   {
     this._appConfigChangeListener.remove(listener);
   }
 
   private synchronized void fireAppConfigChangeEvent()
   {
     Iterator<AppConfigChangeListener> listener_iter = this._appConfigChangeListener.iterator();
     while (listener_iter.hasNext()) {
       AppConfigChangeListener listener = (AppConfigChangeListener)listener_iter.next();
       listener.appConfigChanged(this);
     }
   }
 
   public String[] getLoggerNames()
   {
     Object[] object_keys = this._loggerMap.keySet().toArray();
     Arrays.sort(object_keys);
     String[] string_keys = new String[object_keys.length];
     for (int i = 0; i < object_keys.length; i++)
       string_keys[i] = ((String)object_keys[i]);
     return string_keys;
   }
 
   public Logger getLogger(String name)
   {
     if (name == null) return this._defaultLogger;
     Logger logger = (Logger)this._loggerMap.get(name);
     if (logger == null)
       logger = this._defaultLogger;
     return logger;
   }
   public Logger getLogger() {
     return getLogger(null);
   }
 
   public Connection getConnection(String ds_name) throws SQLException
   {
     Properties ds_prop = (Properties)this._jdbcMap.get(ds_name);
 
     String jndi_name = null;
 
     if (ds_prop != null) {
       jndi_name = ds_prop.getProperty("jndi");
       if ((jndi_name == null) || (jndi_name.length() == 0))
         return getJdbcConnectionByDriver(ds_name, ds_prop);
     } else {
       jndi_name = ds_name;
     }
     return getJdbcConnectionByJndi(jndi_name);
   }
 
   private Connection getJdbcConnectionByDriver(String ds_name, Properties jdbc_prop)
     throws SQLException
   {
     String jdbc_driver = jdbc_prop.getProperty("class");
     if (jdbc_driver == null) {
       jdbc_driver = jdbc_prop.getProperty("driver");
     }
     String jdbc_url = jdbc_prop.getProperty("url");
     String jdbc_user = jdbc_prop.getProperty("user");
     String jdbc_password = jdbc_prop.getProperty("password");
 
     if ((jdbc_driver == null) || (jdbc_url == null)) {
       throw new SQLException("Invalid configuration " + ds_name);
     }
     String jdbc_factory = jdbc_prop.getProperty("factory");
     if ((jdbc_factory != null) && (jdbc_factory.length() > 0)) {
       JdbcConnectionFactoryInterface factory = (JdbcConnectionFactoryInterface)this._jdbcFactory.get(ds_name);
 
       if (factory == null) {
         try {
           Class<?> factory_class = Class.forName(jdbc_factory, true, this._classLoader);
           factory = (JdbcConnectionFactoryInterface)factory_class.newInstance();
           factory.init(jdbc_prop);
           this._jdbcFactory.put(ds_name, factory);
         } catch (Throwable t) {
           throw new SQLException("Internal Error: " + t.getMessage());
         }
       }
       return factory.getConnection(jdbc_prop);
     }
     try {
       Class.forName(jdbc_driver, true, this._classLoader);
       if (jdbc_user == null) {
         return DriverManager.getConnection(jdbc_url);
       }
       return DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_password); } catch (ClassNotFoundException e) {
     
     throw new SQLException("Class not found " + e.getMessage());
}
   }
 
   private Connection getJdbcConnectionByJndi(String jndi_name)
     throws SQLException
   {
	 Connection localConnection=null;
     Context ctx = null;
     try {
       ctx = new InitialContext();
       DataSource ds = (DataSource)ctx.lookup(jndi_name);
       localConnection = ds.getConnection();
     }
     catch (NamingException e)
     {
      
       throw new SQLException("Name: " + e.getMessage() + " is not found in JNDI.");
     } finally {
       if (ctx != null) try {
           ctx.close();
         }
         catch (Exception e) {
         } 
     }

return localConnection;
   }
 
   public XmlConfiguration getXmlConfiguration() {
     return this._xmlConfig;
   }
   public String getLoggerPath() {
     return this._loggerPath;
   }
 
   public ServletContext getServletContext() {
     return this._sc;
   }
 
   public HttpSession getHttpSession()
   {
     return HttpRequestContext.currentContext().getSession();
   }
 
   public HttpServletRequest getHttpServletRequest()
   {
     return HttpRequestContext.currentContext().getRequest();
   }
 
   public HttpServletResponse getHttpServletResponse()
   {
     return HttpRequestContext.currentContext().getResponse();
   }
 
   private void clearJdbcFactory()
   {
     Iterator<JdbcConnectionFactoryInterface> jdbc_iter = this._jdbcFactory.values().iterator();
     while (jdbc_iter.hasNext()) {
       JdbcConnectionFactoryInterface factory = (JdbcConnectionFactoryInterface)jdbc_iter.next();
 
       factory.destroy();
     }
   }
 }

