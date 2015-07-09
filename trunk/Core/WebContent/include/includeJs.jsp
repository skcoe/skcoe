
<%
/* SessionUser user=(SessionUser)session.getAttribute("Login");
String display=(String)session.getAttribute("display"); */
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path ;
%>


<!-- lib javaScript -->
<script type="text/javascript"  src="<%=basePath %>/JsAndCss/JS/jquery-1.11.3.min.js" ></script>
<script type="text/javascript"  src="<%=basePath %>/JsAndCss/JS/bootstrap.min.js" ></script>
<script type="text/javascript"  src="<%=basePath %>/JsAndCss/JS/jquery.uploadfile.min.js" ></script>



<script type="text/javascript"> 
var appCore={};
appCore.path='<%= basePath %>';
appCore.pathService=appCore.path +'/Coe';
</script>







		