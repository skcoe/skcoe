 package th.co.truecorp.vcare.http.servlet;
 
 import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
 
 public class DispatcherHttpServlet extends HttpServlet
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = -7412948765138119955L;
private HashMap<String, HttpServlet> _servletCache;
   private HashMap<String, AppServletConfig> _pathCache;
   private long _lastModified;
 
   public DispatcherHttpServlet()
   {
     this._servletCache = null;
     this._pathCache = null;
 
     this._lastModified = 0L;
   }
 
   public void init(ServletConfig config) throws ServletException
   {
     super.init(config);
     ServletContext sc = config.getServletContext();
     this._servletCache = new HashMap<String, HttpServlet>();
     this._pathCache = new HashMap<String, AppServletConfig>();
     ApplicationContext app_ctx = ApplicationContext.getContext(sc);
     this._lastModified = app_ctx.lastModified();
   }
 
   public void service(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException
   {
     ServletContext sc = getServletContext();
     ApplicationContext app_ctx = ApplicationContext.getContext(sc);
     app_ctx.saveCurrentContext();
     HttpRequestContext req_ctx = new HttpRequestContext(req, resp);
     req_ctx.saveCurrentContext();
 
     if (this._lastModified < app_ctx.lastModified())
     {
       clearServletCache();
       this._lastModified = app_ctx.lastModified();
     }
 
     if (app_ctx.getAppConfigFile() == null)
     {
       resp.sendError(500, "configuration file is not found.");
       return;
     }
 
     ClassLoader app_cl = app_ctx.getClassLoader();
     ClassLoader thread_cl = Thread.currentThread().getContextClassLoader();
     try {
       Thread.currentThread().setContextClassLoader(app_cl);
 
       URL url = new URL("http", "truecorp.co.th", 80, req.getRequestURI());
       URI uri = new URI(url.toString());
 
       String req_path = uri.getPath();
       String context_path = req.getContextPath();
       String servlet_path = req.getServletPath();
 
       req_path = req_path.substring(context_path.length() + servlet_path.length());
       if ((req_path == null) || (req_path.length() == 0)) {
         req_path = "/";
       }
 
       AppServletConfig servlet_config = (AppServletConfig)this._pathCache.get(req_path);
       if (servlet_config == null)
       {
         servlet_config = app_ctx.getServletConfig(req_path);
         if (servlet_config != null) {
           this._pathCache.put(req_path, servlet_config);
         }
       }
       if (servlet_config == null)
       {
         resp.sendError(404);
         return;
       }
       HttpServlet servlet_handler = (HttpServlet)this._servletCache.get(servlet_config.getServletName());
 
       if (servlet_handler == null)
       {
	              sc.log("Class.forName " + servlet_config.getServletClassName());
         Class<?> servlet_class = Class.forName(servlet_config.getServletClassName(), true, app_cl);
         servlet_handler = (HttpServlet)servlet_class.newInstance();
         try {
           servlet_handler.init(servlet_config);
         } catch (Throwable t) {
           servlet_handler.destroy();
           sc.log("Initiate servlet, " + servlet_config.getServletClassName() + " , fail: " + t.getMessage(), t);
           resp.sendError(500, t.getMessage());
 
           Thread.currentThread().setContextClassLoader(thread_cl); return;
         }
         this._servletCache.put(servlet_config.getServletName(), servlet_handler);
       }
 
       DispatcherHttpServletRequest dispath_req = new DispatcherHttpServletRequest(req, servlet_config.getServletName());
 
       servlet_handler.service(dispath_req, resp);
     }
     catch (Throwable t) {
       sc.log(t.getMessage(), t);
       resp.sendError(500, t.getMessage());
     } finally {
       Thread.currentThread().setContextClassLoader(thread_cl);
     }
   }
 
   public void destroy()
   {
     clearServletCache();
     super.destroy();
   }
 
   private void clearServletCache() {
     Iterator<HttpServlet> servlet_iter = this._servletCache.values().iterator();
     while (servlet_iter.hasNext()) {
       HttpServlet servlet_handler = (HttpServlet)servlet_iter.next();
       servlet_handler.destroy();
     }
     this._servletCache.clear();
     this._pathCache.clear();
   }
 
   private static class DispatcherHttpServletRequest extends HttpServletRequestWrapper
   {
     private String _forwardPath;
 
     public DispatcherHttpServletRequest(HttpServletRequest request, String forward_path)
     {
    super(request);
       this._forwardPath = forward_path;
     }
 
     public String getServletPath()
     {
       return super.getServletPath() + this._forwardPath;
     }
   }
 }

