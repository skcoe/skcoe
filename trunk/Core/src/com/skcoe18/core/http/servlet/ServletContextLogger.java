package com.skcoe18.core.http.servlet;

import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;


public class ServletContextLogger extends Logger
{
	protected ServletContextLogger(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private ServletContext _sc = null;
	private String mLoggerName;

/*	public ServletContextLogger(ServletContext sc)
	{
		
		this._sc = sc;
		this.mLoggerName = this._sc.getServletContextName();
		
	
	}*/

	protected void log(int level, String message, Date created)
	{
		this._sc.log(message);
	}

	protected void init(Properties envs)
			throws InstantiationException
	{
	}
}

