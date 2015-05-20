 package th.co.truecorp.vcare.http.servlet;
 
 import java.io.InputStream;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletContext;
 import org.apache.axis.ConfigurationException;
 import org.apache.axis.EngineConfiguration;
 import org.apache.axis.EngineConfigurationFactory;
 import org.apache.axis.configuration.EngineConfigurationFactoryDefault;
 import org.apache.axis.configuration.FileProvider;
 
 public class EngineConfigurationFactoryServlet extends EngineConfigurationFactoryDefault
 {
   private ServletConfig cfg;
 
   public static EngineConfigurationFactory newFactory(Object param)
   {
     return (param instanceof ServletConfig) ? new EngineConfigurationFactoryServlet((ServletConfig)param) : null;
   }
 
   protected EngineConfigurationFactoryServlet(ServletConfig conf)
   {
     this.cfg = conf;
   }
 
   public EngineConfiguration getServerEngineConfig() {
     return getServerEngineConfig(this.cfg);
   }
 
   private static EngineConfiguration getServerEngineConfig(ServletConfig cfg)
   {
     ServletContext ctx = cfg.getServletContext();
 
     String config_filename = ctx.getInitParameter("WebServiceConfigFileName");
 
     if ((config_filename == null) || (config_filename.length() == 0)) {
       config_filename = "server-config.wsdd";
     }
     FileProvider config = null;
 
     ClassLoader cl = ApplicationContext.currentContext().getClassLoader();
 
     InputStream is = cl.getResourceAsStream(config_filename);
 
     if (is != null) {
       ctx.log("Config file, " + config_filename + ", is found.");
       config = new FileProvider(is);
     } else {
       try {
         String ext_lib_path = ctx.getInitParameter("ExternalLibraryPath");
         ctx.log("Config file, " + config_filename + ", is not found. Create at " + ext_lib_path);
         config = new FileProvider(ext_lib_path, config_filename);
       } catch (ConfigurationException e) {
         ctx.log("Create webservice - config file in external library path", e);
       }
     }
     return config;
   }
 }

