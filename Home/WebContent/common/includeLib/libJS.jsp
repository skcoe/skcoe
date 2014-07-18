<%

	String pathx = request.getContextPath();
	String basePathx = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ pathx ;
	
%>
<script type="text/javascript"  src="<%=basePathx %>/common/js/jquery-1.11.0.min.js" ></script>
<script type="text/javascript"  src="<%=basePathx %>/common/js/bootstrap.min.js" ></script>
<script type="text/javascript"  src="<%=basePathx %>/common/js/offcanvas.js" ></script>
<script type="text/javascript"> var path='<%= basePathx %>';</script>
<script type="text/javascript"  src="<%=basePathx %>/common/libJs/cash.js" ></script>



