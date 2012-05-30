<html>
<head>
<% String pages = request.getParameter("pages"); %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="../../../templete/libinclude.jsp" %>

<title>From</title>
</head>
<body  bgcolor="#1475A3">
<script type="text/javascript">


Ext.onReady(function() {
 
    var configs = [<%=pages%>];
    
    Ext.each(configs, function(config) {
        var element = Ext.getBody().createChild({cls: 'panel-container'});
        
        Ext.createWidget('panel', Ext.applyIf(config, {
            renderTo: element,
            bodyPadding: 7
        }));
    });
});

</script>

</body>
</html>