package com.skcoe18.core.http.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminReloadHttpServlet
 */
public class AdminReloadHttpServlet extends HttpServlet {/**
	 * 
	 */
	private static final long serialVersionUID = 3035287358743979636L;

public void service(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException
	  {
	    ServletContext sc = getServletContext();
	    ApplicationContext app_ctx = ApplicationContext.getContext(sc);

	    File config_file = app_ctx.getAppConfigFile();
	    if (config_file != null)
	      config_file.setLastModified(System.currentTimeMillis());
	    app_ctx.reload();

	    resp.sendRedirect(req.getContextPath() + "/admin/index.jsp");
	  }
}
