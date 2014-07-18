
<%

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path ;
	//String commonPath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path+"/common" ;

%>

<!-- lib CSS -->
<link rel="stylesheet" href="<%=basePath %>/common/css/blitzer/jquery-ui-1.10.3.custom.css">
<link rel="stylesheet" href="<%=basePath %>/common/css/ui.jqgrid.css">
<link rel="stylesheet" href="<%=basePath %>/common/css/bootstrap.css">
<link rel="stylesheet" href="<%=basePath %>/common/css/bootstrap-responsive.css">


<!-- lib javaScript -->
<script type="text/javascript"  src="<%=basePath %>/common/libJs/jquery-1.10.2.min.js" ></script>
<script type="text/javascript"  src="<%=basePath %>/common/libJs/jquery.json-2.4.min.js" ></script>
<script type="text/javascript" src="<%=basePath %>/common/libJs/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/libJs/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src= "<%=basePath %>/common/libJs/grid.locale-en.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/libJs/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/libJs/jquery.placeholder.js"></script>
<script type="text/javascript"> 
var path='<%= basePath %>';
</script>
<script type="text/javascript"  src="<%=basePath %>/common/libJs/cash.js" ></script>







		