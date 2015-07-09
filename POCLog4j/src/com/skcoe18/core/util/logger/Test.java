package com.skcoe18.core.util.logger;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.security.auth.login.Configuration;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.xml.DOMConfigurator;




public class Test {
	private static Logger log = LogManager.getLogger("x1");
	public static void main(String[] args) {
		//Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
		
		/*Properties properties=new Properties();
		  properties.setProperty("log4j.rootLogger","TRACE,stdout");
		  properties.setProperty("log4j.appender.stdout","org.uncommons.ansiconsolelogger.ANSIConsoleAppender");
		  properties.setProperty("log4j.appender.stdout.layout","org.apache.log4j.PatternLayout");
		  properties.setProperty("log4j.appender.stdout.layout.ConversionPattern","%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");
		  PropertyConfigurator.configure(properties);*/
         
      ///////////////////////////////////////////////////////////////////////
		  log.info("Welcome to www.HowToDoInJava.com");
		  log.debug("Welcome to www.HowToDoInJava.com");
		  log.error("Welcome to www.HowToDoInJava.com");
		  log.fatal("Welcome to www.HowToDoInJava.com");
  
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("D:/Varako2/Workspace/POCLog4j/src/com/skcoe18/core/util/logger/log4j.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PropertyConfigurator.configure(props);
		 //DOMConfigurator.configure("log4j-config.xml");
			//getLoggerInfos();
			//getLoggers();
			
			 LoggerRepository r=LogManager.getLoggerRepository();
			  Enumeration enumer=r.getCurrentLoggers();
			  Logger logger=null;
			  while (enumer.hasMoreElements()) {
			    logger=(Logger)enumer.nextElement();
			    System.out.println(logger.getName()+":"+logger.getLevel()+":");
			    /*ResourceBundle rb=logger.getResourceBundle();
			    Enumeration keys = logger.getResourceBundle().getKeys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					String val=rb.getString(key);
					//key2.put(key, val); 
					log.debug("[reloadConfig local > "+key+" : "+val+"]" );
				}*/
			  }
			  
			//This is the root logger provided by log4j
			  Logger rootLogger = Logger.getRootLogger();
			  rootLogger.setLevel(Level.DEBUG);
			   
			  //Define log pattern layout
			  PatternLayout layout = new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n");
			   
			  //Add console appender to root logger
			  rootLogger.addAppender(new ConsoleAppender(layout));
			  try
			  {
			  //Define file appender with layout and output log file name
			  RollingFileAppender fileAppender = new RollingFileAppender(layout, "demoApplication.log");
			   
			  //Add the appender to root logger
			  rootLogger.addAppender(fileAppender);
			  }
			  catch (IOException e)
			  {
			  System.out.println("Failed to add appender !!");
			  }
			  
			  
			  
		
	}
	public static List<String> getLoggerInfos() throws IOException {
		 System.out.println("getLoggerInfos");
		  List<String> stringListOfloggers=new ArrayList<String>();
		  stringListOfloggers.add(LogManager.getRootLogger().getName());
		  Enumeration<?> loggers=LogManager.getLoggerRepository().getCurrentLoggers();
		  while (loggers.hasMoreElements()) {
		    Logger logger=(org.apache.log4j.Logger)loggers.nextElement();
		    String nameAndLevel=logger.getName() + "=" + logger.getLevel();
		    System.out.println(nameAndLevel);
		    stringListOfloggers.add(nameAndLevel);
		  }
		  return stringListOfloggers;
		}
	public static List<org.apache.log4j.Logger> getLoggers() throws IOException {
		  List<org.apache.log4j.Logger> listOfloggers=new ArrayList<org.apache.log4j.Logger>();
		  listOfloggers.add(LogManager.getRootLogger());
		  Enumeration<?> loggers=LogManager.getLoggerRepository().getCurrentLoggers();
		  while (loggers.hasMoreElements()) {
		    org.apache.log4j.Logger logger=(org.apache.log4j.Logger)loggers.nextElement();
		    listOfloggers.add(logger);
		    System.out.println(logger);
		  }
		  return listOfloggers;
		}
	public static String setLoggerLevel(String loggerName,String level,Logger logger) throws IOException {
		  Level newLevel=(level == null || level.equals("null")) ? null : Level.toLevel(level);
		  Level oldLevel=LogManager.getLogger(loggerName).getLevel();
		  if ((newLevel == null && oldLevel == null) || (newLevel != null && newLevel.equals(oldLevel)))   return level;
		  //log(logger,Level.DEBUG,"set logger level: ",loggerName,"=",newLevel);
		  LogManager.getLogger(loggerName).setLevel(newLevel);
		  return level;
		}
	
/*	public static void configureLog4j(Configuration conf,String loggerConfKeyPrefix,Level level) throws Throwable {
		  if (conf == null)   conf=new Configuration(false);
		  if (loggerConfKeyPrefix == null)   loggerConfKeyPrefix="";
		  setConfDefaultValue(conf,loggerConfKeyPrefix + "log4j.rootLogger","INFO,console");
		  setConfDefaultValue(conf,loggerConfKeyPrefix + "log4j.threshhold","ALL");
		  setConfDefaultValue(conf,loggerConfKeyPrefix + "log4j.appender.console","org.apache.log4j.ConsoleAppender");
		  setConfDefaultValue(conf,loggerConfKeyPrefix + "log4j.appender.console.target","System.err");
		  setConfDefaultValue(conf,loggerConfKeyPrefix + "log4j.appender.console.layout","org.apache.log4j.PatternLayout");
		  setConfDefaultValue(conf,loggerConfKeyPrefix + "log4j.appender.console.layout.ConversionPattern","%d{yy-MM-dd HH:mm:ss,SSS} %p %c{2}: %m%n");
		  Properties props=new Properties();
		  Map<String,String> confKeyAndValues=getConf(conf,loggerConfKeyPrefix);
		  for (  String key : confKeyAndValues.keySet()) {
		    props.setProperty(key.substring(loggerConfKeyPrefix.length()),confKeyAndValues.get(key));
		  }
		  new PropertyConfigurator().doConfigure(props,LogManager.getLoggerRepository());
		  for (  String key : confKeyAndValues.keySet()) {
		    Utilities.log(logger,level,"set ",key.substring(loggerConfKeyPrefix.length()),"=",confKeyAndValues.get(key));
		  }
		}*/
	
	public static boolean isLog4jConfigured(){
		  Enumeration<?> appenders=Logger.getRootLogger().getAllAppenders();
		  if (appenders.hasMoreElements()) {
		    return true;
		  }
		  Enumeration<?> loggers=LogManager.getCurrentLoggers();
		  while (loggers.hasMoreElements()) {
		    Logger c=(Logger)loggers.nextElement();
		    if (c.getAllAppenders().hasMoreElements()) {
		      return true;
		    }
		  }
		  return false;
		}
	
	public String[] getCurrentLoggers(){
		  List<String> loggers=new ArrayList<String>();
		  Enumeration en=LogManager.getLoggerRepository().getCurrentLoggers();
		  while (en.hasMoreElements()) {
		    Logger l=(Logger)en.nextElement();
		    loggers.add(l.getName());
		  }
		  Collections.sort(loggers);
		  return loggers.toArray(new String[loggers.size()]);
		}

}
