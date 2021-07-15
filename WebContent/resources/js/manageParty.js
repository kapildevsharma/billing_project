$(document).ready(function() {
	$("#btn_party").click(function() {
		if (partyvalidation()) // Calling validation function.
		{
			$('#partyForm').attr('action', 'viewParty');
			$("#partyForm").submit();
		}
	});
	$(window).load(function() { 
		getPagination();
	});
});

function partyvalidation(){
	var partyname = $.trim($("#name").val());
	var tin_no = $.trim($("#tin_no").val());
	var contact_no = $.trim($("#contact_no").val());
	$("#partyErr").html(' ');
//	alert("partyname :" + partyname + " tin_no:: " + tin_no + " contact_no :" + contact_no);

	if (partyname == '') {
		$("#name").focus();
		$("#partyErr").html("<span style='color:red'>* Enter Party Name</span>");
		return false;
	} if (tin_no == '') {
		$("#tin_no").focus();
		$("#partyErr").html("<span style='color:red'>* Enter TIN Number</span>");
		return false;
	} if (contact_no == '') {
		$("#contact_no").focus();
		$("#partyErr").html("<span style='color:red'>* Enter Contact No.</span>");
		
		return false;
	} 
	
	if(contact!=''){
		if(!$.isNumeric(contact)){
			$("#contact").focus();
			$("#partyErr").html("<span style='color:red'>* Enter valid contact number</span>");
			return false;
		}
		else {
			if(contact.length <=10){
				alert(contact.length);
				$("#contact").focus();
				$("#partyErr").html("<span style='color:red'>* Enter valid 10 digits contact number.</span>");
				return false;
			}
		}
	}
	
	
	return true;
}

function submitDetails(id){
	if(id!=null){
		$("#updatePartyId").val(id);
		$("#partyForm").submit();
	}else{
		return false;
	}
}

function deleteParty(id){
	MyAjax.deleteParty(id,{
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
	MyAjax.getPaginatyionParty(totalrecord,cpage,limit,{
		async : false,
		callback : function(data) {
			$("#pagelimit").html(data);
		}
	});
}
