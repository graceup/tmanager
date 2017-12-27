 /**
  *获取url中的参数
  * 
  */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//定时对象
var intervalObj=null;
//文件名称
var fileName;
//文件大小(上次跟踪的位置)
var actualLength=0;

//初始化
$(function() {
	
	fileName=getUrlParam('fileName');
	
	
	intervalObj=setInterval(function(){
		 getLog(actualLength,fileName);
	 },2000);
	
})


/**
 * 获取日志
 * 
 */
function getLog(lastLength,fileName){
	
	//获取Tomcat自带信息
	$.ajax({
		type : "GET",
		dataType:"json",
		url : "log?oper=trace&lastKnownLength="+lastLength+"&fileName="+fileName,
		success : function(data) { 
			console.log(data);
			
			if(actualLength!=data.fileInfo.actualLength){
			
				$("#scroller").append(data.data);
				$("#logPath").html(data.fileInfo.fileName);
				$("#lastModified").html(data.fileInfo.lastModified);
				$("#fileSize").html(data.fileInfo.fileSize);
				
				var div = document.getElementById('scroller');
	            div.scrollTop = div.scrollHeight;
	            
	            actualLength=data.fileInfo.actualLength;
			}
            
            
			
		}
	});
	
}


/**
 * 切换跟踪日志
 */
function pause(){
	
	 if(intervalObj==null){
		 intervalObj=setInterval(function(){
			 
			 getLog(actualLength,fileName);
			 
		 },2000);
		 $("#btnpause").html("暂停");
	 }else{
		 window.clearInterval(intervalObj);
		 intervalObj=null;
		 $("#btnpause").html("继续");
	 }
	
}

 
 
/**
 * 刷新页面
 */
function refresh(){
	window.location=window.location.href;
}
 
/**
 * 放大字体
 */
function zoomin(){
	var e = $("#scroller");
	if (e) {
		var old_size = e.css('font-size').replace("px",""); 
		var new_size = (old_size - 1 + 3);
		if (new_size <= 32) {
			e.css('font-size', new_size);
		}
	}
}
/**
 * 缩小字体
 */
function zoomout(){
	var e = $("#scroller");
	if (e) {
		var old_size = e.css('font-size').replace("px",""); 
		var new_size = (old_size - 3 + 1);
		if (new_size >= 4) {
			e.css('font-size', new_size);
		}
	}
}


/**
 * 清空日志
 */
function clearlog(){
	$("#scroller").html("");
}

   
 