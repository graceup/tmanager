
/**
 * 日志
 * 
 * @param fileName
 */
function traceLog(fileName){
	
	$.ajax({
		type : "GET",
		url : "log?oper=trace&fileName="+fileName,
		success : function(data) { 
			console.log(data);
			getInformation();
		}
	});
}

/**
 * 删除日志
 * 
 * @param fileName
 */
function deleteLog(fileName){
	
	$.ajax({
		type : "GET",
		dataType:"json",
		url : "log?oper=delete&fileName="+fileName,
		success : function(data) { 
			console.log(data);
			if(data.code==0){
				alert('删除成功。');
				getInformation();
			}else{
				alert('删除不成功，请重试。');
			}
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
		url : "log?oper=list",
		success : function(data) { 
			console.log(data);
			
			var infoArray=data.list;
			
			console.log(infoArray); 
			
			var infoHtml='';
			
			for(x in infoArray){
				
				
				var oneLine=infoArray[x];
						
				infoHtml+='<tr class="gradeX">'+
						'	<td>'+oneLine.fileName+'</td>'+
						'	<td>'+oneLine.fileSize+'</td>'+
						'	<td>'+oneLine.lastModified+'</td>'+
						'	<td>'+
						'		<div class="am-btn-toolbar">'+
						'			<div class="am-btn-group am-btn-group-xs">'+
						'				<a href="log?oper=download&fileName='+oneLine.fileName+'" target="_blank" '+
						'					class="am-btn am-btn-default am-btn-xs am-text-secondary">'+
						'					<span class="am-icon-cloud-download"></span>下载'+
						'				</a>'+
						'				<a href="trace-log.html?fileName='+oneLine.fileName+'" target="_blank" '+
						'					class="am-btn am-btn-default am-btn-xs am-hide-sm-only">'+
						'					<span class="am-icon-align-justify"></span> 跟踪'+
						'				</a>'+
						'			<button onclick="deleteLog(\''+oneLine.fileName+'\')"  '+
						'					class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">'+
						'					<span class="am-icon-trash-o"></span> 删除'+
						'				</button>'+
						'			</div>'+
						'		</div>'+
						'	</td>'+
						'</tr>';		
						 
					
				
				
			}
			
			
			$("#logs-info").html(infoHtml);
			
		},
		dataType:"json",
		beforeSend : function(xhr) { }
	});
	
	
}


//初始化
$(function() {
	
	
	
	getInformation();
	
})
   
 