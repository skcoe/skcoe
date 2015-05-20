package th.co.truecorp.vcare.http.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





/**
 * Servlet implementation class Core
 */
public class Core extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Core() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		  doProcess( request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doProcess( request,  response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* ServletContext scontext=getServletContext();
		 ServletConfig config = getServletConfig();
		 String initial = config.getInitParameter("initial");
		 String initial2 = config.getInitParameter("name");
		 System.out.println(initial);
		 System.out.println(initial2);
		 System.out.println( scontext.getServerInfo());*/
		 ServletContext sc = getServletContext();
		    ApplicationContext app_ctx = ApplicationContext.getContext(sc);

		    File config_file = app_ctx.getAppConfigFile();
		    if (config_file != null)
		      config_file.setLastModified(System.currentTimeMillis());
		    app_ctx.reload();

		 
		 
		 
		 
	}

}
