 package com.skcoe18.core.http.servlet;
 
 import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.co.truecorp.vcare.http.HttpRequest;
import th.co.truecorp.vcare.http.HttpResponse;
 
 public abstract class VCareHttpServlet extends HttpServlet
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = -7488026334758001060L;

public void service(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException
   {
     HttpRequest http_req = createHttpRequest(req);
     HttpResponse http_res = handle(ApplicationContext.currentContext(), http_req);
 
     mapHttpResponseToHttpServletResponse(http_res, resp);
   }
 
   public abstract HttpResponse handle(ApplicationContext paramApplicationContext, HttpRequest paramHttpRequest)
     throws VCareHttpServletException;
 
   private HttpRequest createHttpRequest(HttpServletRequest req) throws ServletException, IOException
   {
     HttpRequest http_req = new HttpRequest(req.getMethod(), req.getRequestURI(), req.getProtocol());
 
     for (Enumeration<?> e = req.getHeaderNames(); e.hasMoreElements(); )
     {
       String header_key_name = (String)e.nextElement();
       http_req.setHeader(header_key_name, req.getHeader(header_key_name));
     }
 
     if (req.getMethod().equals("POST"))
     {
       byte[] post_data = readToEnd(req.getInputStream(), req.getContentLength());
       http_req.setPostData(post_data);
     }
     return http_req;
   }
 
   private void mapHttpResponseToHttpServletResponse(HttpResponse http_res, HttpServletResponse resp)
     throws ServletException, IOException
   {
     if (http_res.getStatusCode() >= 400) {
       resp.sendError(http_res.getStatusCode(), http_res.getStatusMessage());
       return;
     }
 
     resp.setStatus(http_res.getStatusCode());
 
     String[] header_keys = http_res.getHeaderNames();
 
     for (int i = 0; i < header_keys.length; i++)
     {
       String[] header_values = http_res.getHeaders(header_keys[i]);
       for (int j = 0; j < header_values.length; j++) {
         resp.addHeader(header_keys[i], header_values[j]);
       }
     }
     if (http_res.getContentLength() > 0) {
       resp.setContentLength(http_res.getContentLength());
       resp.getOutputStream().write(http_res.getResponseBody());
     }
   }
 
   private byte[] readToEnd(ServletInputStream in, int content_length)
     throws IOException
   {
     byte[] post_data = null;
 
     if (content_length >= 0) {
       post_data = new byte[content_length];
       int total_count = 0;
       while (total_count < content_length)
       {
         int read_count = in.read(post_data, total_count, content_length - total_count);
         total_count += read_count;
       }
     } else {
       byte[] buffer = new byte[1024];
       ByteArrayOutputStream out = new ByteArrayOutputStream();
       while (in.available() > 0) {
         int read_count = in.read(buffer, 0, buffer.length);
         out.write(buffer, 0, read_count);
       }
      post_data = out.toByteArray();
      out.close();
     }
    return post_data;
   }
 }

