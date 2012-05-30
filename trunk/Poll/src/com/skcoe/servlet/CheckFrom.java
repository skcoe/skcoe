package com.skcoe.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * Servlet implementation class CheckFrom
 */
public class CheckFrom extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckFrom() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,  response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("ID");
		String pages = "{" +
				"width: 800," +
				"height: 800," +
				"border: true," +
				"frame: true," +
				"title: 'Login'," +
				"items: [" +
					"{" +
					"fieldLabel:'Username'," +
					"xtype:'textfield'," +
					"name:'someField'," +
					"anchor:'100%'" +
					"}" +
				"]" +
				"}";
		request.setAttribute("pages", pages);
		response.setContentType( "text/html" );
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("age", new Integer(100));

		JSONArray list = new JSONArray();
		list.add("msg 1");
		list.add("msg 2");
		list.add("msg 3");

		obj.put("messages", list);
		String jsonOut = obj.toString();
		PrintWriter out = response.getWriter();
		//response.sendRedirect("http://localhost:8080/Poll/pages/from/from1.jsp");
		out.println(jsonOut);
		
		out.close();
	}


}
