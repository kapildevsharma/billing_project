$(document).ready(function() {
	$("#btn_employee").click(function() {
		if (empvalidation()) // Calling validation function.
		{
			$('#empForm').attr('action', 'viewEmployee');
			$("#empForm").submit();
		}
	});
	$(window).load(function() { 
		getPagination();
	});
	
});

function empvalidation(){
	var empname = $.trim($("#name").val());
	var emppassword = $.trim($("#pwd").val());
	var emailid = $.trim($("#emailid").val());
	var empdob = $.trim($("#dob").val());
	var empdoj = $.trim($("#doj").val());
	$("#userErr").html(' ');
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var contact = $.trim($("#contact").val());
//	alert("username :" + username + " userpassword:: " + userpassword + " userdob :" + userdob);

	if (empname == '') {
		$("#name").focus();
		$("#userErr").html("<span style='color:red'>* Enter User Name</span>");
		$("#userErr").addClass("has-error");
		return false;
	} if (emppassword == '') {
		$("#pwd").focus();
		$("#userErr").html("<span style='color:red'>* Enter User Password</span>");
		return false;
	} 
	if(emailid==''){
		$("#emailid").focus();
		$("#userErr").html("<span style='color:red'>* Enter Email ID</span>");
		return false;
	}else{
		if (!regex.test(emailid)) {
			$("#emailid").focus();
			$("#userErr").html("<span style='color:red'>* Enter Valid Email ID</span>");
			return false;
		}
	}
	if (empdob == '') {
		$("#dob").focus();
		$("#userErr").html("<span style='color:red'>* Select Birth Date </span>");
		
		return false;
	} 
	
	if (empdoj == '') {
		$("#doj").focus();
		$("#userErr").html("<span style='color:red'>* Select Joining Date</span>");
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
	$("#updateEmpID").val(id);
	$("#empForm").submit();
}

function deleteEmployee(id){
	MyAjax.deleteEmployee(id,{
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
	MyAjax.getPaginatyionEmployee(totalrecord,cpage,limit,{
		async : false,
		callback : function(data) {
			$("#pagelimit").html(data);
		}
	});
}
