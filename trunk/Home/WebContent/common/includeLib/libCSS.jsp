<%

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path ;
	
%>

<link rel="stylesheet" href="<%=basePath %>/common/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath %>/common/css/offcanvas.css">
