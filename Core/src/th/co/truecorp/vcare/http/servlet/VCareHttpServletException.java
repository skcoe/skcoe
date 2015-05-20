 package th.co.truecorp.vcare.http.servlet;
 
 import java.io.IOException;
 
 public class VCareHttpServletException extends IOException
 {
   /**
	 * 
	 */
	private static final long serialVersionUID = 7897884820781188669L;

public VCareHttpServletException(String message)
   {
     super(message);
   }
 
   public VCareHttpServletException(String message, Throwable cause) {
     super(message);
     initCause(cause);
   }
 
   public VCareHttpServletException(Throwable cause) {
     super(cause.getMessage());
     initCause(cause);
   }
 }

