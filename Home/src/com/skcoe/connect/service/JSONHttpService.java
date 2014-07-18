package com.skcoe.connect.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import org.json.JSONObject;

public class JSONHttpService {
	public static String excutePost(String targetURL, JSONObject urlParameters,Proxy proxy)
	  {
		
		/*String urlParameters =
		        "fName=" + URLEncoder.encode("???", "UTF-8") +
		        "&lName=" + URLEncoder.encode("???", "UTF-8")*/
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //set Proxy
	      //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.19.20.21", 80));
	    	
	      //Create connection
	      url = new URL(targetURL);
	      if(proxy!=null){
	    	  connection = (HttpURLConnection)url.openConnection(proxy);
	      }else{
	    	  connection = (HttpURLConnection)url.openConnection();  
	      }
	      //connection.setReadTimeout(36000);
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	      String p="request="+urlParameters.toString();
	      connection.setRequestProperty("Content-Length", "" + Integer.toString(p.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
	      wr.writeBytes(p);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
}
