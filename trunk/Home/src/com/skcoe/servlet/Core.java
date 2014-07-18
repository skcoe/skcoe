package com.skcoe.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.skcoe.connect.service.JSONHttpService;



/**
 * Servlet implementation class Core
 */
@WebServlet("/Core")
public class Core extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9057052813874771494L;
	static Logger log = Logger.getLogger(Core.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Core() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//######  request	##################	
		// |-request
		//     |-header(c)
		//     |  |->key
		//     |-body(c)
		//     |  |->service
		//     |  |-input(c)
		//     |  |  |->param(s)
		//####################################
		//######  response	 #################
		// |-response
		//     |->status
		//     |->msg
		//     |->sessionid
		//     |-output(c)
		//     |   |->param(s)
		//####################################


		String sessionId=request.getSession().getId();
		String pRequest = request.getParameter("request");
		log.info("[Start]["+sessionId+"][Web_request  >> "+pRequest+"]");

		JSONObject resp=new JSONObject();
		boolean backPage=false;
		boolean download=false;
		RequestDispatcher rd=null;
		if(pRequest==null){ //web

			//new function


		}else{//call service
			JSONObject req=null;
			try {
				req = new JSONObject(pRequest);
			} catch (JSONException e) {
				catchMsg( sessionId, e.getMessage(), "0001",resp);
			}

			if(req!=null){
				String respStr=JSONHttpService.excutePost("http://localhost:8080/ServiceHardware/ServiceCore", req, null);
				try {
					resp=new JSONObject(respStr);
				} catch (JSONException e) {
					catchMsg( sessionId, e.getMessage(), "0002",resp);
				}
			}




		}

		if(backPage){
			rd.forward(request, response);
		}else if(download){


		}else{
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(resp.toString());
		}








		log.info("[END  ]["+sessionId+"][Web_response >> "+resp.toString()+"]");
	}
	
	public void catchMsg(String sessionId,String msg,String code,JSONObject j) {
		try {
			j.put("msg", msg);
			j.put("sessionId", sessionId);
			j.put("status", code);
			j.put("output", "[]");
		} catch (JSONException e1) {
			log.info("[ERROR]["+sessionId+"]["+msg+"]");
		}

	}
/*
	public LoginInfo checkUserLogin(String id, String password) {
		// HttpSession session = request.getSession(true);

		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();

		HiSpeedM hispeedM = (HiSpeedM) session.getAttribute("HiSpeedM");
		LoginInfo login = new LoginInfo();

		String validateDigit = siebelM.getProperty("validate.login.digit");

		if (validateDigit != null && validateDigit.equals("Y")
				&& id.length() <= 2) {
			login.setStatus("2");
			login.setErrormessage("Invalid User Login");
			session.setAttribute("HiSpeedM", hispeedM);
			return login;
		}

		String returnValue = "";
		PropertySet requestProp = new PropertySet();
		requestProp.setType("RequestHiSpeedService");
		requestProp.setProperty("service_name", ADSL_SERVICE_NAME);

		PropertySet requestPropChild = new PropertySet();
		requestPropChild.setType("DealerLogin");
		requestPropChild.setProperty("login", id);
		requestPropChild.setProperty("password", password);
		requestProp.addChild(requestPropChild);

		PropertySet responseProp = new PropertySet();
		String url = siebelM.getHosturl();
		try {
			XmlHttpService ws = new XmlHttpService(url);
			log.info(url + " > " + XmlUtil.propertySetToXmlString(requestProp));
			responseProp = ws.send(requestProp);
			log.info(url + " < " + XmlUtil.propertySetToXmlString(responseProp));
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			returnValue = ThaiUtil.ASCII2Unicode((String) siebelM.getErrorMsg()
					.get("error.system.00000"));
			login.setStatus("2");
			login.setErrormessage(returnValue);
			return login;
		}
		String status = responseProp.getProperty("status");
		if (status == null)
			status = responseProp.getChild(0).getProperty("status");
		log.debug("Status Respond : " + status);

		if (status.equals("0")) {
			String saleRole = responseProp.getProperty("role");
			log.info(saleRole);
			if (saleRole.endsWith("Administrator")) {
				SaleInfoM adminInfo = new SaleInfoM();
				adminInfo.setSaleId(ThaiUtil.ASCII2Unicode(id));
				adminInfo.setSaleName(responseProp.getProperty("dealername"));
				adminInfo.setSaleChannel(responseProp
						.getProperty("salechannel"));
				adminInfo.setSaleRole(saleRole);
				try {
					hispeedM.setAdminInfo(adminInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				returnValue = "Admin";
			} else {
				if (!hispeedM.getSaleChannel().startsWith("Shop")
						&& !hispeedM.getSaleChannel().startsWith("Event")) {
					hispeedM.setSaleChannel(responseProp
							.getProperty("salechannel"));
				}

				hispeedM.setSaleRole(responseProp.getProperty("role"));
				hispeedM.setSaleName(responseProp.getProperty("dealername"));
				returnValue = "NotAdmin";
			}
			hispeedM.setSaleId(ThaiUtil.ASCII2Unicode(id));
			hispeedM.setSalePassWd(ThaiUtil.ASCII2Unicode(password));

		} else {
			returnValue = responseProp.getProperty("errormessage");
			if (returnValue == null) {
				returnValue = responseProp.getChild(0).getProperty(
						"errormessage");
			}
		}

		login.setStatus(status);
		login.setErrormessage(returnValue);

		session.setAttribute("HiSpeedM", hispeedM);

		return login;
	}
*/
}
