/**
 * 用中文分号替换英文分号、中英文逗号或者回车
 * 
 * @param mobiles
 * @returns {String}
 */
function replaceSeperator(mobiles) {
    var i;
    var result = "";
    var c;
    for (i = 0; i < mobiles.length; i++) {
        c = mobiles.substr(i, 1);
        if (c == ";" || c == "," || c == "，" || c == "\n")
            result = result + "；";
        else if (c != "\r")
            result = result + c;
    }
    return result;
}

//初始化
$(function() { 
	
	//获取Tomcat自带信息
	$.ajax({
		type : "GET",
		url : "text/serverinfo",
		success : function(data) { 
//			console.log(data);
			
			var infoArray=replaceSeperator(data).split('；');
			
//			console.log(infoArray); 
			
			var infoHtml='';
			
			for(x in infoArray){
				
				var oneLine=infoArray[x].split(':');

				var key=oneLine[0];
				var info=oneLine[1];
				
//				console.log(key); 
				if(key=="Tomcat Version"){
					infoHtml+='Tomcat版本:'+info+'<br>';
				}else if(key=="OS Name"){
					infoHtml+='系统名称:'+info+'<br>';
				}else if(key=="OS Version"){
					infoHtml+='系统版本:'+info+'<br>';
				}else if(key=="OS Architecture"){
					infoHtml+='操作系统体系结构:'+info+'<br>';
				}else if(key=="JVM Version"){
					infoHtml+='JVM版本:'+info+'<br>';
				}else if(key=="JVM Vendor"){
					infoHtml+='JVM提供商:'+info+'<br>';
				}
				
				
			}
			
			
			$("#text-info").html(infoHtml);
			
		},
		beforeSend : function(xhr) {

			//Authorization Header is present: Basic YWRtaW46YWRtaW4=
			//Decoded Username:Password= admin:admin

			xhr.setRequestHeader("Authorization", "Basic "
					+ "YWRtaW46YWRtaW4=");

		}
	});
	
	
 
	
	//获取Tomcat自带信息
	$.ajax({
		type : "GET",
		url : "info",
		success : function(data) { 
			console.log(data);
			
		var infoHtml='<ul> '+
					 '	<li>当前时间: '+data.baseInfo.currentTime+'</li>'+
					 '	<li>操作系统运行时间: '+data.baseInfo.osRunTime+'</li>'+
					 '	<li>Tomcat运行时间: '+data.baseInfo.tomcatRunTime+'</li>'+
					 '	<li>目录: '+data.baseInfo.tomcatPath+'</li>'+
					 '</ul>';
			
		$("#text-info").before(infoHtml);
			
		},
		dataType:"json",
		beforeSend : function(xhr) { }
	});
	
	
	
	
})
   
 