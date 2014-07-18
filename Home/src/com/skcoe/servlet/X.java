package com.skcoe.servlet;

import org.json.JSONException;
import org.json.JSONObject;

import com.skcoe.connect.service.JSONHttpService;

public class X {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("123456789012345678901234567890".substring(0, 28)+"..");
		JSONObject j=null;
		try {
			j = new JSONObject("{header:{key:'xxx'},body:{service:'ON_LED',input:{led:[1]}}}");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String resp=JSONHttpService.excutePost("http://localhost:8080/ServiceHardware/ServiceCore", j, null);
	System.out.println(resp);
	
	}

}
