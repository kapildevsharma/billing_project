$(document).ready(function() {
	$("#btn_user").click(function() {
		validateUser(); // Calling validation function.
	});
});
$(document).keypress(function(e) {
    if(e.which == 13) {
    	validateUser(); // Calling validation function.
    }
});
function validateUser(){
	var username = $.trim($("#name").val());
	var password = $.trim($("#pwd").val());
	$("#userErr").html("");
	$("#userErr").addClass("");
	if($('#logoutmsg').is(':visible')) {
		$("#logoutmsg").hide();
	}
	if(username==""){
		$("#name").focus();
		$("#userErr").addClass("col-xs-12 alert alert-danger");
		$("#userErr").html("<strong>* Please enter username</strong>");
		return false;
	}
	if(password==""){
		$("#pwd").focus();
		$("#userErr").addClass("col-xs-12 alert alert-danger");
		$("#userErr").html("<strong>* Please enter password</strong>");
		return false;
	}
	MyAjax.validateUser(username,password,{
		async : false,
		callback : function(loginData) {
			console.log("loginData :"+loginData.username+ " id : "+loginData.id+" active :"+loginData.isActive);
			if(loginData ==null || loginData==''){
				$("#userErr").addClass("col-xs-12 alert alert-danger");
				$("#userErr").html("<strong>* Invalid UserName Or Password</strong>");
				return false;
			}else {
				if(loginData.isActive==1){
					$("#userErr").addClass("col-xs-12 alert alert-danger");
					$("#userErr").html("<strong>* Deactive User. Please contact Admininstrator</strong>");
					return false;
				}
				var data0 = {"loginUserId": loginData.id};
				var json = JSON.stringify(data0 ); 
				$.ajax({
					 beforeSend: function(xhr) {
					        xhr.setRequestHeader("Accept", "application/json");
					        xhr.setRequestHeader("Content-Type", "application/json");
					 },
					 type: "POST",
					 url: "loginUser",
					 data: json,
					 async: false, 
					 contentType: "application/x-www-form-urlencoded",
					 dataType: "json",
					 success: function(msg) {
						 location.href = "addUser";
					 },
					 error:function(msg){
						 return false;
					 }
					 
				});
			}
		}
	});
	
}