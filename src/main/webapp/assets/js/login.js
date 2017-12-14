//初始化
$(function() { 
	
	
	 $('#login-btn').on('click', function() {
		 
		
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
		 
		window.location='index.html';
		 
		 $.ajax({
			 type : "POST",
			 url : "login",
			 success : function(data) {
				 console.log(data);
			 },
			 beforeSend : function(xhr) {
				 
				 //Authorization Header is present: Basic YWRtaW46YWRtaW4=
				 //Decoded Username:Password= admin:admin
				 
				 xhr.setRequestHeader("Authorization", "Basic "
						 + "YWRtaW46YWRtaW4=");
				 
			 }
		 });
		 
		 
		 
		 
     });
	
	
	
	
	
	
})
 
