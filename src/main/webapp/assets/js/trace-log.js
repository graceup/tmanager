
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
		url : "log?oper=trace&lastKnownLength=0&fileName="+fileName,
		success : function(data) { 
			console.log(data);
			 
			
			$("#scroller").append(data.data);
			
		},
		dataType:"json",
		beforeSend : function(xhr) { }
	});
	
	/*
	 var myScroll = new IScroll('#wrapper', {
         scrollbars: true,
         mouseWheel: true,
         interactiveScrollbars: true,
         shrinkScrollbars: 'scale',
         preventDefault: false,
         fadeScrollbars: true
     });
	*/
	
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

var fileName=null;

//初始化
$(function() {
	
	fileName=getUrlParam('fileName');
	
	
	getInformation();
	
})
   
 