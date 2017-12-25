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

/**
 * 启动应用
 * @param appName
 */
function startApp(appName){
	
	$.ajax({
		type : "GET",
		url : "text/start?path="+appName,
		success : function(data) { 
			console.log(data);
			getInformation();
		}
	});
}

/**
 * 停止应用
 * @param appName
 */
function stopApp(appName){
	
	$.ajax({
		type : "GET",
		url : "text/stop?path="+appName,
		success : function(data) { 
			console.log(data);
			getInformation();
		}
	});
}
/**
 * 重载应用
 * @param appName
 */
function reloadApp(appName){
	
	$.ajax({
		type : "GET",
		url : "text/reload?path="+appName,
		success : function(data) { 
			alert('重载成功');
			console.log(data);
			getInformation();
		}
	});
}

/**
 * 卸载应用
 * @param appName
 */
function undeployApp(appName){
	
	$.ajax({
		type : "GET",
		url : "text/undeploy?path="+appName,
		success : function(data) { 
			alert('卸载成功');
			console.log(data);
			getInformation();
		}
	});
}

/**
 * 获取基本信息
 * 
 */
function getInformation(){
	
	
	//获取Tomcat自带信息
	$.ajax({
		type : "GET",
		url : "text/list",
		success : function(data) { 
			console.log(data);
			
			var infoArray=replaceSeperator(data).split('；');
			
			console.log(infoArray); 
			
			var infoHtml='';
			
			for(x in infoArray){
				
				if(x>0){
					
				
					var oneLine=infoArray[x].split(':');
	
					var key=oneLine[0];
					
					console.log(key); 
					if(!(key=='/'||key=='')){
						
						console.log(oneLine);
						var status=oneLine[1];
						
						if(status=='stopped'){
							status='停止';
						}else if(status=='running'){
							status='运行中';
						}
						
						
						infoHtml+='<tr class="gradeX">'+
								'<td><a href='+key+' target="_blank">'+key+'</a></td>'+
								'<td><span class="am-badge tpl-badge-success">'+status+'</span>'+
								'</td>'+
								'<td>'+oneLine[2]+'</td>'+
								'<td>';
								
								if(key!="/tmanager"){
								
									if(oneLine[1]=='running'){	
							
									infoHtml+=		'	<div class="am-btn-toolbar">'+
									'		<div class="am-btn-group am-btn-group-xs">'+
									'			<button onclick="stopApp(\''+key+'\')"  '+
									'				class="am-btn am-btn-default am-btn-xs am-text-secondary">'+
									'				<span class="am-icon-stop"></span>停止'+
									'			</button>'+
									'			<button onclick="reloadApp(\''+key+'\')"  '+
									'				class="am-btn am-btn-default am-btn-xs am-hide-sm-only">'+
									'				<span class="am-icon-refresh am-icon-spin"></span> 重载'+
									'			</button>'+
									'			<button onclick="undeployApp(\''+key+'\')"  '+
									'				class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">'+
									'				<span class="am-icon-trash-o"></span> 卸载'+
									'			</button>'+
									'		</div>'+
									'	</div>';
									
									}else{
										 
										infoHtml+=		'	<div class="am-btn-toolbar">'+
										'		<div class="am-btn-group am-btn-group-xs">'+
										'			<button onclick="startApp(\''+key+'\')"  '+
										'				class="am-btn am-btn-default am-btn-xs am-text-secondary">'+
										'				<span class="am-icon-play"></span>启动'+
										'			</button>'+
										'		</div>'+
										'	</div>';
										
									}
							
								}
								
						infoHtml+=		'</td>'+
							    '</tr>'
						
						
					} 
					
				
				}
				
			}
			
			
			$("#apps-info").html(infoHtml);
			
		},
		beforeSend : function(xhr) {

			//Authorization Header is present: Basic YWRtaW46YWRtaW4=
			//Decoded Username:Password= admin:admin

			xhr.setRequestHeader("Authorization", "Basic "
					+ "YWRtaW46YWRtaW4=");

		}
	});
	
	
	return;
	
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
			
		$("#base-info").html(infoHtml);

		$("#cpuNum").html(data.cpuInfo.cpuNum);
		$("#cpuFrequency").html(data.cpuInfo.cpuFrequency);
		$("#cpuUsage").html(data.cpuInfo.cpuUsage);

		$("#freeSwapSpaceSize").html(data.menInfo.freeSwapSpaceSize);
		$("#freePhysicalMemorySize").html(data.menInfo.freePhysicalMemorySize);
		$("#totalMemory").html(data.menInfo.totalMemory);
		$("#maxMemory").html(data.menInfo.maxMemory);
		$("#freeMemory").html(data.menInfo.freeMemory);
		$("#totalSwapSpaceSize").html(data.menInfo.totalSwapSpaceSize);
		$("#memUsed").html(data.menInfo.memUsed);
		$("#committedVirtualMemorySize").html(data.menInfo.committedVirtualMemorySize);
		$("#totalPhysicalMemorySize").html(data.menInfo.totalPhysicalMemorySize);
		
		
		var diskInfoHtml='';
		
		for(x in data.diskInfo){
			var oneDiskInfo=data.diskInfo[x];
			console.log(oneDiskInfo);
			diskInfoHtml+='<tr>'+
							'<td>'+oneDiskInfo.path+'</td>'+
							'<td>'+oneDiskInfo.usableSpace+'</td>'+
							'<td>'+oneDiskInfo.freeSpace+'</td>'+
							'<td>'+oneDiskInfo.totalSpace+'</td>'+
							'<td class="font-green bold">'+oneDiskInfo.percent+'</td>'+
							'</tr>';
		}
	
	
		$('#disk-info').html(diskInfoHtml);
		
		
			
		},
		dataType:"json",
		beforeSend : function(xhr) { }
	});
	
	
	
}

var intervalObj=null;

//初始化
$(function() {
	
	
	
	getInformation();
	
})
   
 