


v.app["APP_1"]=function(){//AppOnOff
	//alert("AppOnOff.js");
	function onEvent(serv,id,v){
		cs({header:{key : 'XXX'},body : {service : serv,input : {"led" :[{"id":id,"status":v}]}}	}, function(data) { 
			if(v=='off'){
				$( "#relayOnoff div a[rowid="+id+"]" ).attr('v',"on");
				$( "#relayOnoff div a[rowid="+id+"]" ).empty();
				$( "#relayOnoff div a[rowid="+id+"]" ).text('OFF»');
			}else{
				$( "#relayOnoff div a[rowid="+id+"]" ).attr('v',"off");
				$( "#relayOnoff div a[rowid="+id+"]" ).empty();
				$( "#relayOnoff div a[rowid="+id+"]" ).text('ON»');
			}
			
			
			}, null, "request");
	}
	function cs(data,fnSuccess,callBackground,paramName){

		//console.log(da);
		var aoData = new Array();
		//aoData.push({ "name": "id", "value": id });
		if(v.funcUtil.isNull(paramName)){
			aoData.push({ "name": "config", "value": JSON.stringify(data)});
		}else{
			aoData.push({ "name": paramName , "value": JSON.stringify(data)});
		}


		$.ajax({
			type: "POST",
			url: v.coreService,
			data: aoData ,
			dataType: "json",
			"success": function( data,textStatus,jqXHR){

				var status=data.status;
				var msg=data.msg;

				fnSuccess(data,textStatus,jqXHR);


			},
			"beforeSend": function(){
				/*if( v.funcUtil.isNull(callBackground) || !callBackground){
						$( "#loading" ).dialog( "open" );
						loadall=loadall+1;
					}*/
			},
			"complete": function(data){
				/*if( v.funcUtil.isNull(callBackground) || !callBackground){
						loadall=loadall-1;
						if(loadall<1){
							$( "#loading" ).dialog( "close" ); 
						}
					}
					if(!v.funcUtil.isNull(config.complete) ){
		        		config.complete(data);
		        	}*/
			},
			error: function( jqXHR,  textStatus,  errorThrown){
				/*popUpMsg(textStatus);*/
			}
		});

	};

	$( "#relayOnoff div a" ).click(function() {
		var id = $(this).attr('rowid');
		var v = $(this).attr('v');
		//alert(id+":"+v);

		var serv="ON_LED";
		if("on"==v){
			serv="OFF_LED";
		}

		onEvent(serv,id,v);

		//load apps all

	});
	if (v.funcUtil.isEmpty($("#list"))) {
		$("#list").append( "<option>Select</option>" );
		cs({header:{key : 'XXX'},body : {service : "LIST_PORT",input : {}}	}, function(data) {
			//console.log(data);
			for ( var int = 0; int < data.output.length; int++) {
				var item=data.output[int];
				$("#list").append( "<option  value='"+item.port+"'  >"+item.port+"@"+item.type+"</option>" );
			}
			
		}, null, "request");
	}
	
	

	$("#list").on('change', function() {
		var v= $(this).val();
		cs({header:{key : 'XXX'},body : {service : "SET_PORT",input : {port:v}}	}, function(data) {}, null, "request");
	});
};