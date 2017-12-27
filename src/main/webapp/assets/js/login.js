//初始化
$(function() { 
	
	
	 $('#login-btn').on('click', function() {
		 login();
	 });
	 

	 //监听回车事件
	 $(document).keypress(function(e) {
	        if(e.which == 13) {
	        	login();
	        }
	    });

	
})
 
/**
 * 登录
 */
function login(){
	
	var user = $("#doc-ipt-username-1").val();
	var pawd = $("#doc-ipt-pwd-1").val();
	
	if($.trim(user)==''){
		layer.msg("账号不能为空！");
		$("#doc-ipt-username-1").focus();
		return;
	}
	if($.trim(pawd)==''){
		layer.msg("密码不能为空！");
		$("#doc-ipt-pwd-1").focus();
		return;
	}
	 
	 $.ajax({
		 type : "POST",
		 url : "login?oper=login&username="+user+"&password="+pawd,
		 dataType:"json",
		 success : function(data) {
				if(data.code==0){
					window.location='index.html';
				}else{
					layer.msg(data.msg);
				}
			}
	 });
	 
 
}