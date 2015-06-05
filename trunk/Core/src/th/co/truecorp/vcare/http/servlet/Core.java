package th.co.truecorp.vcare.http.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;





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
		JSONObject resp=new JSONObject();
		
		
		String service= request.getParameter("s");//application/json
		service="loadfile";
		 if("loadApp".equals(service)){
			 ServletContext sc = getServletContext();
			    ApplicationContext app_ctx = ApplicationContext.getContext(sc);

			    File config_file = app_ctx.getAppConfigFile();
			    if (config_file != null)
			      config_file.setLastModified(System.currentTimeMillis());
			    app_ctx.reload();
		 }else if("json".equals(service)){
			 
		 }else if("loadfile".equals(service)){
			// reads input file from an absolute path
		        String filePath = "D:/ext-lib/js/h.js";
		        File downloadFile = new File(filePath);
		        FileInputStream inStream = new FileInputStream(downloadFile);
		         
		        // if you want to use a relative path to context root:
		        String relativePath = getServletContext().getRealPath("");
		        System.out.println("relativePath = " + relativePath);
		         
		        // obtains ServletContext
		        ServletContext context = getServletContext();
		         
		        // gets MIME type of the file
		        String mimeType = context.getMimeType(filePath);
		        if (mimeType == null) {        
		            // set to binary type if MIME mapping not found
		            mimeType = "application/octet-stream";
		        }
		        System.out.println("MIME type: " + mimeType);
		         
		        // modifies response
		        response.setContentType(mimeType);
		        response.setContentLength((int) downloadFile.length());
		         
		        // forces download
		        String headerKey = "Content-Disposition";
		        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		        response.setHeader(headerKey, headerValue);
		         
		        // obtains response's output stream
		        OutputStream outStream = response.getOutputStream();
		         
		        byte[] buffer = new byte[4096];
		        int bytesRead = -1;
		         
		        while ((bytesRead = inStream.read(buffer)) != -1) {
		            outStream.write(buffer, 0, bytesRead);
		        }
		         
		        inStream.close();
		        outStream.close(); 
		 }else{
			 String filePath = "D:/ext-lib/js/h.js";
		        File downloadFile = new File(filePath);
		        String r= FileUtils.readFileToString(downloadFile, "UTF-8");
		        System.out.println(r);
		        try {
					resp=new JSONObject(r);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 response.setContentType("application/json");
			 response.setCharacterEncoding("UTF-8");
			 PrintWriter writer = null;
			 try {
				 writer = response.getWriter();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
			 writer.write(resp.toString());
		 }
		 
		 
		 
	}

}
