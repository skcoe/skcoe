
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta content="IE=edge" http-equiv="X-UA-Compatible">
<meta content="width=device-width, initial-scale=1" name="viewport">
<meta content="" name="description">
<meta content="" name="author">
<link href="../../assets/ico/favicon.ico" rel="shortcut icon">
<title>Login</title>
<%@ include file="../../common/includeLib/libCSS.jsp"%>
</head>
<body>
<div id="app">
<!--#APP########## APP Common ##############-->
	<div rowId='common' class='app' id='APP0'>
		<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button class="navbar-toggle" data-target=".navbar-collapse" data-toggle="collapse" type="button"><span class="sr-only">Toggle navigation</span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Project name</a>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="row row-offcanvas row-offcanvas-left">
				<!-- <div id="sidebar" class="col-xs-6 col-sm-3 sidebar-offcanvas"
					role="navigation">
					<div class="list-group">
						<a class="list-group-item active" href="#">Link</a> <a
							class="list-group-item" href="#">Link1</a> <a
							class="list-group-item" href="#">Link2</a> <a
							class="list-group-item" href="#">Link3</a> <a
							class="list-group-item" href="#">Link4</a>
					</div>
				</div>
				<div class="col-xs-12 col-sm-9 ">
					<p class="pull-left visible-xs visible-lg">
						<button class="btn btn-primary btn-xs" data-toggle="offcanvas"
							type="button">Toggle Menu APP</button>
					</p>
				</div> -->
				<!-- ################### menu app ########################-->
				<div class="col-xs-12 col-sm-9 non" >
				<h2>Application</h2>
				<hr>
				<div class="row">
				
				</div>
				
				</div>

				<!-- ################### Page login ########################-->
				<div class="col-xs-12 col-sm-9">
					<div class="row">
						<div class="col-6 col-sm-6 col-lg-3"></div>
						<div class="col-6 col-sm-6 col-lg-6">
							<div class="form-signin" id="formLogin">
								<!-- <input type="hidden" value="{header:{key:'xxx'},body:{service:'LOGIN',input:{}}}" name="request" id="request"> -->
								<h2 class="form-signin-heading">Please sign in</h2>
								<input class="form-control" id='username'  placeholder="UserName" type="text">
								<input class="form-control" id='password'  placeholder="Password" type="password">
								<button id="xxx" class="btn btn-lg btn-primary btn-block" >Sign in</button>
							</div>
						</div>
						<div class="col-6 col-sm-6 col-lg-3"></div>
					</div>
					<br><br><br><br><br><br><br><br>
				</div>

			</div>
			<!-- end pages -->

			<footer>
				<hr>
				<p class="text-center">Item Authorization Configurator designed and created by True Information Technology Company Limited &copy; 2013</p>
			</footer>
		</div>
	</div>
	
<!--#APP########## APP 1 ##############-->
<div class="non app" id='APP1' >

	<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button class="navbar-toggle" data-target=".navbar-collapse"
					data-toggle="collapse" type="button">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Project name</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container"> 
		<div class="row row-offcanvas row-offcanvas-left">
		<div id="sidebar" class="col-xs-6 col-sm-3 sidebar-offcanvas" role="navigation" >
				<div class="list-group">
					<a class="list-group-item active" href="#">Link</a> 
					<a class="list-group-item" href="#">Link1</a> 
					<a class="list-group-item" href="#">Link2</a> 
					<a class="list-group-item" href="#">Link3</a> 
					<a class="list-group-item" href="#">Link4</a> 
				</div>
			</div>
			<div class="col-xs-12 col-sm-9 " >
			<p class="pull-left visible-xs visible-lg">
				<button class="btn btn-primary btn-xs" data-toggle="offcanvas" type="button">Toggle Menu APP</button>
			</p>
			<select  id='list' ></select>
			
			</div>
			
			<!-- page -->
			<div class="col-xs-12 col-sm-9" >
				
				<div class="jumbotron">
					<h1>Hello, AppOnOff!</h1>
					<p>on off led by relay</p>
				</div>
				<div class="row" id='relayOnoff'>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Relay 1</h2>
						<a class="btn btn-default" role="button" rowid='1' v='off'>ON »</a>
					
					</div>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Relay 2</h2>
						
							<a class="btn btn-default" role="button" rowid='2' v='off'>ON »</a>
						
					</div>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Relay 3</h2>
						
							<a class="btn btn-default" role="button" rowid='3' v='off'>ON »</a>
						
					</div>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Relay 4</h2>
						
							<a class="btn btn-default" role="button" rowid='4' v='off'>ON »</a>
						
					</div>
					
					
				</div>
			</div>

			<!-- #Page################## Page login ########################-->
			<div class="col-xs-12 col-sm-9" style="display: none;">
				<div class="row">
					<div class="col-6 col-sm-6 col-lg-3"></div>
					<div class="col-6 col-sm-6 col-lg-6">
						<form class="form-signin" method="POST"
							action="<%=basePath%>/Core">
							<input type="hidden"
								value="{header:{key:'xxx'},body:{service:'LOGIN',input:{}}}"
								name="request" id="request">
							<h2 class="form-signin-heading">Please sign in</h2>
							<input class="form-control" id='username' autofocus=""
								required="" name="username" placeholder="UserName" type="text">
							<input class="form-control" id='password' required=""
								name="password" placeholder="Password" type="password">
							<label class="checkbox"> <input type="checkbox"
								value="remember-me"> Remember me
							</label>
							<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
								in</button>
						</form>
					</div>
					<div class="col-6 col-sm-6 col-lg-3"></div>
				</div>
				<br><br><br><br><br><br><br><br><br><br><br>
			</div>
			
		</div><!-- end pages -->
		
<footer>
<hr>
<p class="text-center">Item Authorization Configurator designed and created by True Information Technology Company Limited &copy; 2013</p>
</footer>
</div>
</div>

<!--#APP########## APP 2 ##############-->
<div class="non app" id='APP2' >
	<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button class="navbar-toggle" data-target=".navbar-collapse"
					data-toggle="collapse" type="button">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Project name</a>
			</div>
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container"> 
		<div class="row row-offcanvas row-offcanvas-left">
		<div id="sidebar" class="col-xs-6 col-sm-3 sidebar-offcanvas" role="navigation" >
				<div class="list-group">
					<a class="list-group-item active" href="#">Link</a> 
					<a class="list-group-item" href="#">Link1</a> 
					<a class="list-group-item" href="#">Link2</a> 
					<a class="list-group-item" href="#">Link3</a> 
					<a class="list-group-item" href="#">Link4</a> 
				</div>
			</div>
			<div class="col-xs-12 col-sm-9 " >
			<p class="pull-left visible-xs visible-lg">
				<button class="btn btn-primary btn-xs" data-toggle="offcanvas" type="button">Toggle Menu APP</button>
			</p>
			</div>
			<div class="col-xs-12 col-sm-9" style="display: none;">
				
				<div class="jumbotron">
					<h1>Hello, world!</h1>
					<p>This is an example to show the potential of an offcanvas
						layout pattern in Bootstrap. Try some responsive-range viewport
						sizes to see it in action.</p>
				</div>
				<div class="row">
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Heading</h2>
						<p>Donec id elit non mi porta gravida at eget metus. Fusce
							dapibus, tellus ac cursus commodo, tortor mauris condimentum
							nibh, ut fermentum massa justo sit amet risus. Etiam porta sem
							malesuada magna mollis euismod. Donec sed odio dui.</p>
						<p>
							<a class="btn btn-default" role="button" href="#">View
								details »</a>
						</p>
					</div>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Heading</h2>
						<p>Donec id elit non mi porta gravida at eget metus. Fusce
							dapibus, tellus ac cursus commodo, tortor mauris condimentum
							nibh, ut fermentum massa justo sit amet risus. Etiam porta sem
							malesuada magna mollis euismod. Donec sed odio dui.</p>
						<p>
							<a class="btn btn-default" role="button" href="#">View
								details »</a>
						</p>
					</div>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Heading</h2>
						<p>Donec id elit non mi porta gravida at eget metus. Fusce
							dapibus, tellus ac cursus commodo, tortor mauris condimentum
							nibh, ut fermentum massa justo sit amet risus. Etiam porta sem
							malesuada magna mollis euismod. Donec sed odio dui.</p>
						<p>
							<a class="btn btn-default" role="button" href="#">View
								details »</a>
						</p>
					</div>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Heading</h2>
						<p>Donec id elit non mi porta gravida at eget metus. Fusce
							dapibus, tellus ac cursus commodo, tortor mauris condimentum
							nibh, ut fermentum massa justo sit amet risus. Etiam porta sem
							malesuada magna mollis euismod. Donec sed odio dui.</p>
						<p>
							<a class="btn btn-default" role="button" href="#">View
								details »</a>
						</p>
					</div>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Heading</h2>
						<p>Donec id elit non mi porta gravida at eget metus. Fusce
							dapibus, tellus ac cursus commodo, tortor mauris condimentum
							nibh, ut fermentum massa justo sit amet risus. Etiam porta sem
							malesuada magna mollis euismod. Donec sed odio dui.</p>
						<p>
							<a class="btn btn-default" role="button" href="#">View
								details »</a>
						</p>
					</div>
					<div class="col-6 col-sm-6 col-lg-4">
						<h2>Heading</h2>
						<p>Donec id elit non mi porta gravida at eget metus. Fusce
							dapibus, tellus ac cursus commodo, tortor mauris condimentum
							nibh, ut fermentum massa justo sit amet risus. Etiam porta sem
							malesuada magna mollis euismod. Donec sed odio dui.</p>
						<p>
							<a class="btn btn-default" role="button" href="#">View
								details »</a>
						</p>
					</div>
				</div>
			</div>

<!-- ################### Page login ########################-->
			<div class="col-xs-12 col-sm-9">
				<div class="row">
					<div class="col-6 col-sm-6 col-lg-3"></div>
					<div class="col-6 col-sm-6 col-lg-6">
						<form class="form-signin" method="POST"
							action="<%=basePath%>/Core">
							<input type="hidden"
								value="{header:{key:'xxx'},body:{service:'LOGIN',input:{}}}"
								name="request" id="request">
							<h2 class="form-signin-heading">Please sign in</h2>
							<input class="form-control" id='username' autofocus=""
								required="" name="username" placeholder="UserName" type="text">
							<input class="form-control" id='password' required=""
								name="password" placeholder="Password" type="password">
							<label class="checkbox"> <input type="checkbox"
								value="remember-me"> Remember me
							</label>
							<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
								in</button>
						</form>
					</div>
					<div class="col-6 col-sm-6 col-lg-3"></div>
				</div>
				<br><br><br><br><br><br><br><br><br><br><br>
			</div>
			
		</div><!-- end pages -->
		
<footer>
<hr>
<p class="text-center">Item Authorization Configurator designed and created by True Information Technology Company Limited &copy; 2013</p>
</footer>
</div>
</div>
</div>
	<%@ include file="../../common/includeLib/libJS.jsp"%>
	<script type="text/javascript"  src="<%=basePathx %>/js/AppOnOff.js" ></script>
	<script type="text/javascript"  src="<%=basePathx %>/common/libJs/home.js" ></script>
	
</body>
</html>
