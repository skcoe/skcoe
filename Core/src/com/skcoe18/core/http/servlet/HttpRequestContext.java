 package com.skcoe18.core.http.servlet;
 
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 
 public class HttpRequestContext
 {
   private static final ThreadLocal<HttpRequestContext> _reqThreadLocal = new ThreadLocal<HttpRequestContext>();
   private HttpServletRequest _httpRequest;
   private HttpServletResponse _httpResponse;
 
   public HttpRequestContext(HttpServletRequest req, HttpServletResponse res)
   {
     this._httpRequest = req;
     this._httpResponse = res;
   }
 
   public static HttpRequestContext currentContext()
   {
    return (HttpRequestContext)_reqThreadLocal.get();
   }
 
   public void saveCurrentContext()
   {
     _reqThreadLocal.set(this);
   }
   public HttpServletRequest getRequest() {
     return this._httpRequest; } 
   public HttpServletResponse getResponse() { return this._httpResponse; } 
   public HttpSession getSession() { return this._httpRequest.getSession();
   }
 }

