//初始化
$(function() { 
	
	/*
	 */
	 $('#submit-button').on('click', function() {
		 
	
		 $('#deploy-form').ajaxForm({
				 beforeSubmit: function(formData, jqForm, options){
					 
					
					 
					 
					 console.log(formData);
					 var fileValue=$('#doc-form-file').val();
					 
					 if(fileValue==''){
						 alert('请选择war包');
						 return false;
					 }
					 var fileType = fileValue.substr(fileValue.indexOf("."));
					 if(fileType!='.war'){
						 alert('请选择war包');
						 return false;
					 }
					 
					 layer.msg('上传中...', {
						  icon: 16
						  ,shade: 0.7
						  ,time: 0
						});
					 
				 },
				 success:function(data) {
					 console.log(data);
					var html = $.parseHTML(data);
//					console.log(html);
					var msg=html[13].childNodes[1].childNodes[0].childNodes[3].childNodes[0].childNodes[0].textContent;
					
					var msgString='';
					console.log(msg);
					if(msg=='OK'){
						msgString=("部署成功");
					}else{
						msgString=("部署不成功："+msg);
					}
					
					 layer.closeAll();
					 
					 layer.alert(msgString, {
						    skin: 'layui-layer-lan'
						    ,closeBtn: 0
						    ,anim: 4 //动画类型
						  });
					 
					 
					
	         
	     }});
	
	 });

})
   
 