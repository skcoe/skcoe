<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="description">
<meta content="" name="author">
<title>Login</title>
<%@ include file="../common/includeLib/libJqWithBootstrap.jsp"%>
<link rel="stylesheet" href="<%=basePath %>/common/css/login.css"> 
</head>
<body>
    <div class="container">
      <form class="form-signin"  method="POST"  action="<%=basePath %>/Core">
       <input type="hidden" value="{header:{key:'xxx'},body:{service:'LOGIN',input:{}}}" name="request" id="request">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input class="form-control" id='username' autofocus="" required=""  name="username" placeholder="UserName" type="text">
        <input class="form-control" id='password' required="" name="password" placeholder="Password" type="password">  
        <label class="checkbox"> <input type="checkbox" value="remember-me"> Remember me </label>     
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
    </div>
    
    
	
</body>
</html>



