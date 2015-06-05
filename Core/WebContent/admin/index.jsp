
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
<%@ include file="../include/includeCss.jsp"%>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <form class="navbar-form navbar-right">
            <div class="form-group">
              <input placeholder="Email" class="form-control" type="text">
            </div>
            <div class="form-group">
              <input placeholder="Password" class="form-control" type="password">
            </div>
            <button type="submit" class="btn btn-success">Sign in</button>
          </form>
        </div><!--/.navbar-collapse -->
      </div>
    </nav>

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
      <div class="container">
        <h1>Application</h1>
      </div>
    </div>

	<div class="container" id='containerAppType'>
		<!-- Example row of columns -->
		
		<div class="row">
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title"><button id='test' type="button" class="btn btn-default"><span class="glyphicon  glyphicon-link" aria-hidden="true"></span></button>  Panel title Admin </h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>

			</div>
			<div class="col-md-4">
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="panel panel-warning">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
			</div>
		</div>
<nav>
  <ul class="pager">
    <li class="previous"><a href="#"><span aria-hidden="true">&larr;</span> Older</a></li>
    <li ><a href="#">Newer <span aria-hidden="true">&rarr;</span></a></li>
     <li>2</li>
    <li class="next"><a href="#">Newer <span aria-hidden="true">&rarr;</span></a></li>
  </ul>
  
</nav>
		<hr>

		<footer>
		<p>© Company 2014</p>
		</footer>
	</div>
	<!-- /container -->

</body>
<%@ include file="../include/includeJs.jsp"%>
    <script>
					$(document).ready(function()
{
						var $test = $( "#test" );
						 
						function handler1() {
						  console.log( "handler1:"+appCore.path );
						 /*  $.ajax({
							  method: "POST",
							  url: appCore.path+"/Core",
							  dataType: "script",
							  success:function(  data,  textStatus,  jqXHR ){
								  appCore.xxx=data;
								  console.log( data ); // Data returned
								  console.log( textStatus ); // Success
								  console.log( jqxhr.status ); // 200
								  console.log( "Load was performed." );
								  
								  x();
							  },
							  error:function(jqXHR,  textStatus,  errorThrown ){
								  console.log( textStatus ); // Data returned
								  console.log( errorThrown ); // Success
								  console.log( jqXHR.status ); // 200
							  }
							}); */
							 $.getScript(appCore.path+"/Core", function(e) {
								 loadJS();
								 appCore.x();
								 
							});
								
						}
						 
						
						 
						$test.on( "click", handler1 );
});
									
								/* 	$.ajax({
										  method: "GET",
										  url: appCore.path,
										  dataType: "script"
										}); */
				</script>
</html>