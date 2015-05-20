 package th.co.truecorp.vcare.http.servlet;
 
 import java.io.IOException;
 import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class DelegateHttpServlet extends HttpServlet
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = -9044610200245747341L;
private String _services_path;
 
   public void init(ServletConfig config)
     throws ServletException
   {
     super.init(config);
    String services_path = config.getInitParameter("services.path");
     this._services_path = services_path;
   }
 
   public void service(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     String servlet_path = request.getServletPath();
 
    RequestDispatcher dispatcher = request.getRequestDispatcher(this._services_path + servlet_path);
    dispatcher.forward(request, response);
   }
 }

