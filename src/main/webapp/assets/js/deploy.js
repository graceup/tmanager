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
					 
				 },
				 success:function(data) {
					 console.log(data);
					var html = $.parseHTML(data);
//					console.log(html);
					var msg=html[13].childNodes[1].childNodes[0].childNodes[3].childNodes[0].childNodes[0].textContent;
					
					console.log(msg);
					if(msg=='OK'){
						alert("部署成功");
					}else{
						alert("部署不成功："+msg);
					}
					
	         
	     }});
	
	 });

})
   
 