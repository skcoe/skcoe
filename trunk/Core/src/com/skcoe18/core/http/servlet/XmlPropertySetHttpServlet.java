 package com.skcoe18.core.http.servlet;
 
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import javax.servlet.ServletException;
 import javax.servlet.ServletInputStream;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.xml.transform.TransformerException;
 import org.xml.sax.SAXException;
 import th.co.truecorp.vcare.util.PropertySet;
 import th.co.truecorp.vcare.util.XmlUtil;
 
 public abstract class XmlPropertySetHttpServlet extends HttpServlet
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = -7049415361643935494L;
protected HttpServletRequest _httpServletRequest = null;
   protected HttpServletResponse _httpServletResponse = null;
 
   public void service(HttpServletRequest req, HttpServletResponse resp)
     throws ServletException, IOException
   {
     this._httpServletRequest = req;
     this._httpServletResponse = resp;
 
     if (!req.getMethod().equals("POST")) {
       resp.sendError(405);
       return;
     }
 
     byte[] post_bytes = readToEnd(req.getInputStream(), req.getContentLength());
 
     PropertySet req_propset = null;
     try {
       req_propset = XmlUtil.xmlBytesToPropertySet(post_bytes);
     } catch (SAXException e) {
       resp.sendError(400, "The request content is invalid xml format, " + e.getMessage());
       return;
     }
 
     PropertySet res_propset = new PropertySet();
 
     service(req_propset, res_propset);
     byte[] res_bytes = null;
     try {
       res_bytes = XmlUtil.propertySetToXmlBytes(res_propset);
     } catch (TransformerException e) {
       resp.sendError(500, "Error Message : " + e.getMessage());
       return;
     }
 
     resp.setContentType("text/xml; charset=UTF-8");
 
     ServletOutputStream out = resp.getOutputStream();
 
     out.write(res_bytes);
     out.flush();
   }
 
   public abstract void service(PropertySet paramPropertySet1, PropertySet paramPropertySet2)
     throws ServletException, IOException;
 
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

