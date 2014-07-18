

var v={};
v["app"]={};
v["pages"]={};
v["pCash"]={};
v["sel"]={};
v["filterDelay"]={};
v["load"]=0;
v["OpenPages"]={};
v["authorization"]={};
v["defaultPage"]='pCampaignManagement';
v["funcUtil"]={};
v["path"]=path;
v["coreService"]=v.path+'/Core';
v["loadJs"]=function (config){
	/* request
	 * 
	 * config.param */
	
	var aoData = new Array();
	aoData.push({ "name": "request", "value": JSON.stringify(config.param) });
	$.ajax({
		type: "POST",
		data: aoData ,
		url: v.coreService,
		success: function(data, textStatus, jqxhr){
		},
		dataType: "script"
	});
};





v.funcUtil.isEmpty= function ( el ){
    return !$.trim(el.html());
};
v.funcUtil.isNull=function(a){
	var c=false;
	if(a==null||a==undefined){
		c=true;
	}
	return c;
};
v.funcUtil.setScreenOpen = function (config){
	if(!v.funcUtil.isNull(config.Screen)){
		if(v.funcUtil.isNull(v.OpenPages["Screen"])){
			v.OpenPages["Screen"]={};
		}
		
		v.OpenPages["Screen"][config.Screen]="Open";
	}
	
};

v.funcUtil.checkScreenOpenOld=function (config){
	if(v.funcUtil.isNull(v.OpenPages["Screen"])){
		return false;
	}else if(v.funcUtil.isNull(v.OpenPages["Screen"][config.Screen])){
		return false;
	}else{
		return true;
	}
};
v.funcUtil.popUpMsg = function (msg,id,timeClose){
	
	if(!v.funcUtil.isNull(timeClose)){
		if(v.funcUtil.isNull(id)){
			$( "#infomationx").empty();
			$( "#infomationx" ).dialog( "option", "buttons", {"OK": function() { $( "#infomationx" ).dialog( "close" );}});
			$( "#infomationx").append(msg);
			$( "#infomationx" ).dialog( "open" );
			setTimeout(function(){
				$( "#infomationx" ).dialog( "close" );
			}, timeClose);
		}else{
			$( "#"+id).empty();
			$( "#"+id ).dialog( "option", "buttons", {"OK": function() { $( "#"+id ).dialog( "close" );}});
			$( "#"+id).append(msg);
			$( "#"+id ).dialog( "open" );
			setTimeout(function(){
				$( "#"+id ).dialog( "close" );
			}, timeClose);
		}
	}else{
		if(v.funcUtil.isNull(id)){
			$( "#infomationx").empty();
			$( "#infomationx" ).dialog( "option", "buttons", {"OK": function() { $( "#infomationx" ).dialog( "close" );}});
			$( "#infomationx").append(msg);
			$( "#infomationx" ).dialog( "open" );
		}else{
			$( "#"+id).empty();
			$( "#"+id ).dialog( "option", "buttons", {"OK": function() { $( "#"+id ).dialog( "close" );}});
			$( "#"+id).append(msg);
			$( "#"+id ).dialog( "open" );
		}
	}
	
	
};

v.funcUtil.setPageSelect =function(config) {
	if(config!=undefined&&config!=null){
		if(v.sel[config.page]==undefined){
			v.sel[config.page]={};
		}

		if(v.funcUtil.isNull(config.xid)){
			v.sel[config.page][config.id]=config.data;
		}else{
			if(v.funcUtil.isNull(v.sel[config.page][config.id])){
				v.sel[config.page][config.id]={};
			}

			v.sel[config.page][config.id][config.xid]=config.data;
		}

	}
};
v.funcUtil.getPageSelect =function (config) {
	var data=null;
	if(config!=undefined&&config!=null){
		if(v.sel[config.page]!=undefined){
			
			if(v.funcUtil.isNull(config.xid)){
				if(v.sel[config.page][config.id]!=undefined){
					data=v.sel[config.page][config.id];
				}
				
			}else{
				if(!v.funcUtil.isNull(v.sel[config.page][config.id])){
					data=v.sel[config.page][config.id][config.xid];
				}
				
			}
		}
	}
	
	return data;
};
v.funcUtil.filterObj=function (data,value){
	var inline=[];
	
	if(!v.funcUtil.isNull(data)&& data.length >0){
		
		if(v.funcUtil.isNull(value)||''==value){
			inline=data;
		}else{
			var se=""+value;
			se=se.toUpperCase();
			for ( var i = 0; i < data.length; i++) {
				var check =false;
				for (var key in data[i]) {
					if (data[i].hasOwnProperty(key)) {
						var str=data[i][key];
						if(!(str instanceof  Object)){
							str=str.toUpperCase();
							if(str.indexOf(se)>=0){
								check=true;
								break;
							}
						}else{
							//console.log(str);
						}
						
					}
				}

				if(check){
					inline.push(data[i]);
				}
			}
		}
	}
return inline;
	 
};

v.funcUtil.setPCash =function (data,config){
//config => {key:[{name:"page",val:"cc"},{name:"p1",val:"cc"},{name:"p2",val:"cc"}]}
	//var level=0;
	var pCash=v.pCash;
	var len =config.key.length;
	for ( var i = 0; i < len; i++) {
		var k=config.key[i];
		if(i==(len-1)){
			pCash[k.name]=data;
		}else if(v.funcUtil.isNull(k.val)||""==k.val){
			pCash[k.name]=data;
			break;
		}else{
			if(v.funcUtil.isNull(pCash[k.name])){
				pCash[k.name]={};
			}
			pCash=pCash[k.name];
		}
	}
	
	
};

v.funcUtil.getPCash = function (config) {
	//config => {load:true,key:[{name:"page",val:"cc"},{name:"p1",val:"cc"},{name:"p2",val:"cc"}]}
	var gidData=null;
	var pCash=v.pCash;
	var len =config.key.length;
	for ( var i = 0; i < len; i++) {
		var k=config.key[i];
		if(i==(len-1)){
			gidData=pCash[k.name];
		/*}else if(v.funcUtil.isNull(k.val)||""==k.val){
			gidData=pCash[k.name];
			break;*/
		}else if(v.funcUtil.isNull(pCash[k.name])){
			gidData=pCash[k.name];
			break;

		}else{
			pCash=pCash[k.name];
		}
	}
	
	  
	 return gidData;
};

v.funcUtil.callDB =function (config) {
	
	var background=config.background;
	var newObject = jQuery.extend({}, config.key);
	$.ajax({
        type: "POST",
        url: coreService,
        data: newObject ,
        dataType: "json",
        "success": function( data,textStatus,jqXHR){
        	//console.log(data);
        	var status=data.status;
        	var msg=data.msg;
        	if("0"!=status){
        		v.funcUtil.popUpMsg(msg,"infomation");
        	}else{
        		v.funcUtil.setPCash(data.aaData,config);
        		if(config.callbcak!=undefined||config.callbcak!=null){
    	        		config.callbcak(data.aaData);
    	       }
        	}
        },
        "beforeSend": function(){
        	if(!background){
        		$( "#loading" ).dialog( "open" );
            	v.load=v.load+1;
        	}
        	 if(!v.funcUtil.isNull(config.before)){
	        		config.before();
	        	}
		   },
		 "complete": function(data){
			 if(!background){
				 v.load=v.load-1;
				 if(v.load<1){
					 $( "#loading" ).dialog("close"); 
				 } 
			 }
			
			 
			 if(!v.funcUtil.isNull(config.complete) ){
	        		config.complete(data);
	        	}
		   },
        error: function( jqXHR,  textStatus,  errorThrown){
        	v.funcUtil.popUpMsg(textStatus,"infomation");
        }
    });
};

//{page:"",xid:"",parentid:"",id:"",update:"",load:true}
v.funcUtil.load=function (config){
	var datatemp=null;
	if(!v.funcUtil.isNull(config.load) && config.load!=true){
		datatemp=v.funcUtil.getPCash(config);
	}
		if(v.funcUtil.isNull(datatemp)){
			config.callbcak=function(data){
				if(data!=null ){
	        		config.update(data);
	         	}
			};
			v.funcUtil.callDB(config);
		}else{
			config.update(datatemp);
		}
};

v.funcUtil.filterData =function (config){
	
	var id=config.idinput;
	
	$('#'+config.idinput).keyup(function(){

		$('#loadf_'+config.idinput).remove();
		$('#'+id).after('<img  id="loadf_'+config.idinput+'"  src="'+v.path+'/common/img/loadfilter.gif">');
		var val =this.value;
		function myFunction(){
			
			v.filterDelay[id]=setTimeout(function(){
				
				config.search(val,config.idinput);
				
				},1000);
		}

		function myStopFunction(){
			if(v.filterDelay[id]!=null||v.filterDelay[id]!=undefined){
				clearTimeout(v.filterDelay[id]);
			}
		}
		myStopFunction();
		//if(!isNull(val)&&""!=val){
			myFunction();
		//}
		

	});
};

v.funcUtil.serviceCore =function (data,fnSuccess,callBackground,paramName){

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

v.funcUtil.genApp =function(config){
	
};
v.funcUtil.genPage =function(config){
	
};
v.funcUtil.genPage =function(config){
	
};








