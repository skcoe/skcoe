$(document).ready(function () {
	function login(){
		var username =$("#username").val();
		var password =$("#password").val();
		//v.loadJs({param:{header:{key:'XXX'},body:{service:'LOGIN',input:{user:username,pass:password}}}});
		v.funcUtil.serviceCore({
			header : {
				key : 'XXX'
			},
			body : {
				service : 'LOGIN',
				input : {
					user : username,
					pass : password
				}
			}
		}, function(data) {
			
			
			if(v.funcUtil.isNull(data)||v.funcUtil.isNull(data.output)||v.funcUtil.isNull(data.output.ID)){
				
			}else{
				//load apps all
				
				
				
				v.funcUtil.serviceCore({
					header : {
						key : 'XXX'
					},
					body : {
						service : 'LOAD_MENU_APP',
						input : {
							idlogin : data.output.ID
						}
					}
				}, function(data) {
					//alert('no appsss'+data.output.length);
					
					if(v.funcUtil.isNull(data)||v.funcUtil.isNull(data.output)){
						//error
						alert('no app');
					}else{
						if(data.output.length==1){
							var a=data.output[0];
							v.app["APP_"+a.rowId]();
							var s=$("#APP"+a.rowId);
							//console.log(v.app);
							if(s.length>0){
								//hiden app all
								$("#app .app").addClass("non");
								
								//show app is select
								$("#APP"+a.rowId).removeClass("non");
								//$('#app .app[rowId="APP'+a.rowId+'"]').removeClass("non");
							
							$("html, body").animate({scrollTop:0}, '500', 'swing', function() {});
							}else{
								//TODO gen app
								
							}
						}else if(data.output.length>1) {
							alert("len > 1");
						}else{
							alert('err no app len');
						}
						
					}
					
				}, null, "request");
				
			}
			
		}, null, "request");
	};
	
	$( "#formLogin").keypress(function(e) {
	    if(e.which == 13) {
	    	login();
	    	
	    }
	});
	
	$( "#xxx" ).click(function() {
		login();
		
	});
	$("#formLogin input:text").first().focus();
	

///app
	
	
	
	



});
