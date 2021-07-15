$(document).ready(function() {
	$("#btn_user").click(function() {
		if (uservalidation()) // Calling validation function.
		{
			$('#userForm').attr('action', 'viewUser');
			$("#userForm").submit();
		}
	});
	$(window).load(function() { 
		getPagination();
	});
});

function uservalidation(){
	var username = $.trim($("#name").val());
	var userpassword = $.trim($("#pwd").val());
	var userdob = $.trim($("#dob").val());
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var contact = $.trim($("#contact").val());
	$("#userErr").html(' ');
//	alert("username :" + username + " userpassword:: " + userpassword + " userdob :" + userdob);

	if (username == '') {
		$("#name").focus();
		$("#userErr").html("<span style='color:red'>* Enter User Name</span>");
		$("#userErr").addClass("has-error");
		return false;
	} if (userpassword == '') {
		$("#pwd").focus();
		$("#userErr").html("<span style='color:red'>* Enter User Password</span>");
		return false;
	} if (userdob == '') {
		$("#dob").focus();
		$("#userErr").html("<span style='color:red'>* Select Birth Date</span>");
		return false;
	}  
	if(contact!=''){
		if(!$.isNumeric(contact)){
			$("#contact").focus();
			$("#userErr").html("<span style='color:red'>* Enter valid contact number</span>");
			return false;
		}
		else {
			if(contact.length <=10){
				alert(contact.length);
				$("#contact").focus();
				$("#userErr").html("<span style='color:red'>* Enter valid 10 digits contact number.</span>");
				return false;
			}
		}
	}
	return true;
}

function submitDetails(id){
	if(id!=null){
		$("#updateid").val(id);
		$("#userForm").submit();
	}else{
		return false;
	}
}
function deleteUser(id){
	MyAjax.deleteUser(id,{
		async : false,
		callback : function(data) {
		//	console.log(data);
			if(data ==1){
				location.reload(); 
			}
		}
	});
}

function getPagination(){
	var totalrecord = $("#totalrecord").val();
	var cpage = $("#cpage").val();
	var limit = 5;
	MyAjax.getPaginatyionUser(totalrecord,cpage,limit,{
		async : false,
		callback : function(data) {
			$("#pagelimit").html(data);
		}
	});
}