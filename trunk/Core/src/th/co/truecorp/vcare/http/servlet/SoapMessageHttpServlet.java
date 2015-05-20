 package th.co.truecorp.vcare.http.servlet;
 
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.util.Enumeration;
 import javax.servlet.ServletException;
 import javax.servlet.ServletInputStream;
 import javax.servlet.ServletOutputStream;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.xml.soap.MessageFactory;
 import javax.xml.soap.MimeHeaders;
 import javax.xml.soap.SOAPException;
 import javax.xml.soap.SOAPMessage;
 
 public abstract class SoapMessageHttpServlet extends HttpServlet
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = -1461667657068016347L;
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
 
     SOAPMessage req_message = null;
     SOAPMessage res_message = null;
     try
     {
       MessageFactory mf = MessageFactory.newInstance();
 
       MimeHeaders headers = getHeaders(req);
 
       byte[] postdata_bytes = readToEnd(req.getInputStream(), req.getContentLength());
       ByteArrayInputStream postdata_in = new ByteArrayInputStream(postdata_bytes);
       req_message = mf.createMessage(headers, postdata_in);
       res_message = mf.createMessage();
       postdata_in.close();
     } catch (Throwable t) {
       resp.sendError(400, "The request content is invalid soap format, " + t.getMessage());
       return;
     }
 
     service(req_message, res_message);
 
     resp.setContentType("application/soap+xml; charset=UTF-8");
     ServletOutputStream out = resp.getOutputStream();
     try {
       res_message.writeTo(out);
     } catch (SOAPException e) {
       resp.sendError(500, "Error Message : " + e.getMessage());
       return;
     }
   }
 
   public abstract void service(SOAPMessage paramSOAPMessage1, SOAPMessage paramSOAPMessage2) throws ServletException, IOException;
 
   private static MimeHeaders getHeaders(HttpServletRequest req) {
     Enumeration<?> enums = req.getHeaderNames();
     MimeHeaders headers = new MimeHeaders();
 
     while (enums.hasMoreElements()) {
       String headerName = (String)enums.nextElement();
       String headerValue = req.getHeader(headerName);
       headers.addHeader(headerName, headerValue);
     }
 
     return headers;
   }
 
   private static byte[] readToEnd(ServletInputStream in, int content_length)
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

