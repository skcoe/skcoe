
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

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<div id="fileuploader">Upload</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title Admin</h3>
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
$("#fileuploader").uploadFile({															url : appCore.path
																	+ "/services/upload", // Server URL which handles File uploads
															method : "POST", // Upload Form method type POST or GET.
															enctype : "multipart/form-data", // Upload Form enctype.
															formData : null, // An object that should be send with file upload. data: { key1: 'value1', key2: 'value2' }
															returnType : null,
															allowedTypes : "*", // List of comma separated file extensions: Default is "*". Example: "jpg,png,gif"
															fileName : "file", // Name of the file input field. Default is file
															formData : {},
															dynamicFormData : function() { // To provide form data dynamically
																return {};
															},
															maxFileSize : -1, // Allowed Maximum file Size in bytes.
															maxFileCount : -1, // Allowed Maximum number of files to be uploaded
															multiple : true, // If it is set to true, multiple file selection is allowed. 
															dragDrop : true, // Drag drop is enabled if it is set to true
															autoSubmit : true, // If it is set to true, files are uploaded automatically. Otherwise you need to call .startUpload function. Default istrue
															showCancel : true,
															showAbort : true,
															showDone : true,
															showDelete : false,
															showError : true,
															showStatusAfterSuccess : true,
															showStatusAfterError : true,
															showFileCounter : true,
															fileCounterStyle : "). ",
															showProgress : false,
															nestedForms : true,
															showDownload : false,
															onLoad : function(
																	obj) {
															},
															onSelect : function(
																	files) {
																return true;
															},
															onSubmit : function(
																	files, xhr) {
															},
															onSuccess : function(
																	files,
																	response,
																	xhr, pd) {
															},
															onError : function(
																	files,
																	status,
																	message, pd) {
															},
															onCancel : function(
																	files, pd) {
															},
															downloadCallback : false,
															deleteCallback : false,
															afterUploadAll : false,
															uploadButtonClass : "ajax-file-upload",
															dragDropStr : "<span><b>Drag &amp; Drop Files</b></span>",
															abortStr : "Abort",
															cancelStr : "Cancel",
															deletelStr : "Delete",
															doneStr : "Done",
															multiDragErrorStr : "Multiple File Drag &amp; Drop is not allowed.",
															extErrorStr : "is not allowed. Allowed extensions: ",
															sizeErrorStr : "is not allowed. Allowed Max size: ",
															uploadErrorStr : "Upload is not allowed",
															maxFileCountErrorStr : " is not allowed. Maximum allowed files are:",
															downloadStr : "Download",
															showQueueDiv : false,
															statusBarWidth : 500,
															dragdropWidth : 500
														});
									});
				</script>
</html>