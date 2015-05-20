 package th.co.truecorp.vcare.http.servlet;
 
 import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.co.truecorp.vcare.http.HttpRequest;
import th.co.truecorp.vcare.http.HttpResponse;
 
 public class ProxyHttpServlet extends HttpServlet
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = 7479566452705947383L;
private URL _forward_url;
   private URL _forward_context;
   private String _proxy_host;
   private int _proxy_port;
   private String _proxy_user;
   private String _proxy_password;
   private int _forward_timeout = 90000;
 
   public void init(ServletConfig config)
     throws ServletException
   {
     super.init(config);
 
     String target_url_param = null;
     String target_context_param = null;
     String proxy_port_param = null;
     String timeout_param = null;
 
     target_url_param = getInitParameter("proxyhttpservlet.url.servlet");
     target_context_param = getInitParameter("proxyhttpservlet.url.contextroot");
     this._proxy_host = getInitParameter("proxyhttpservlet.proxy.host");
     this._proxy_user = getInitParameter("proxyhttpservlet.proxy.user");
     this._proxy_password = getInitParameter("proxyhttpservlet.proxy.password");
     proxy_port_param = getInitParameter("proxyhttpservlet.proxy.port");
     timeout_param = getInitParameter("proxyhttpservlet.timeout");
 
     if ((target_url_param == null) || (target_url_param.length() == 0)) {
       if ((target_context_param == null) || (target_context_param.length() == 0))
         throw new ServletException("Parameter [proxyhttpservlet.url.servlet] or Parameter [proxyhttpservlet.url.contextroot] is required.");
       try
       {
         this._forward_context = new URI(target_context_param).toURL();
       } catch (Exception e) {
         throw new ServletException("Parameter [proxyhttpservlet.url.contextroot] is invalid.", e);
       }
     }
     else
     {
       try {
         this._forward_url = new URI(target_url_param).toURL();
       } catch (Exception e) {
         throw new ServletException("Parameter [proxyhttpservlet.url.servlet] is invalid.", e);
       }
     }
 
     if ((timeout_param != null) && (timeout_param.length() > 0)) try {
         this._forward_timeout = Integer.parseInt(timeout_param);
       } catch (Exception e) {
       } if ((this._proxy_host != null) && (this._proxy_host.length() > 0) && (proxy_port_param != null) && (proxy_port_param.length() > 0))
       try {
         this._proxy_port = Integer.parseInt(proxy_port_param);
       }
       catch (Exception e) {
       }
   }
 
   public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     HttpRequest http_req = createHttpRequest(req);
 
     String req_path = req.getRequestURI();
 
     req_path = req_path.substring(req_path.indexOf(req.getContextPath()) + req.getContextPath().length() + req.getServletPath().length());
     if ((req_path == null) || (req_path.length() == 0)) {
       req_path = req.getServletPath();
     }
     String url = null;
     if (this._forward_url != null) {
       url = this._forward_url.toString();
     }
     else if (this._forward_context != null) {
       url = this._forward_context.toString() + req_path;
     }
     if (url == null) throw new ServletException("Parameter [proxyhttpservlet.url.servlet] or Parameter [proxyhttpservlet.url.contextroot is required.");
 
     if (http_req.getQueryString() != null) {
       if (url.indexOf("?") != -1)
         url = url + "&" + http_req.getQueryString();
       else {
         url = url + "?" + http_req.getQueryString();
       }
     }
     http_req.setURL(url);
     if ((this._proxy_host != null) && (this._proxy_host.length() > 0))
     {
       if ((this._proxy_user != null) && (this._proxy_user.length() > 0))
         http_req.setProxy(this._proxy_host, this._proxy_port, this._proxy_user, this._proxy_password);
       else {
         http_req.setProxy(this._proxy_host, this._proxy_port);
       }
     }
 
     HttpResponse http_res = http_req.send(this._forward_timeout);
     mapHttpResponseToHttpServletResponse(http_res, resp);
   }
 
   private HttpRequest createHttpRequest(HttpServletRequest req)
     throws ServletException, IOException
   {
     String req_path = req.getRequestURI();
     if (req.getQueryString() != null) {
       req_path = req_path + "?" + req.getQueryString();
     }
     HttpRequest http_req = new HttpRequest(req.getMethod(), req_path, req.getProtocol());
 
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
     http_req.setFollowRedirects(true);
 
     return http_req;
   }
 
   private void mapHttpResponseToHttpServletResponse(HttpResponse http_res, HttpServletResponse resp)
     throws ServletException, IOException
   {
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

