$(document).ready(function() {
	$("#btn_material").click(function() {
		if (mvalidation()) 	{
			$('#materialForm').attr('action', 'viewMaterial');
			$("#materialForm").submit();
		}
	});
	$(window).load(function() { 
		getPagination();
	});
});

function mvalidation() {
	var mname = $.trim($("#mname").val());
	var mtype = $.trim($("#mtype").val());
	var mprice = $.trim($("#mprice").val());
	var munit = $.trim($("#munit").val());

//	alert("name :" + mname + " type:: " + mtype + " price :" + mprice	+ " unit ::" + munit);
	$("#err").html(' ');
	if (mname == '') {
		$("#mname").focus();
		$("#err").html("<span style='color:red'>* Enter Material Name</span>");
		return false;
	} else if (mtype == '') {
		$("#mtype").focus();
		$("#err").html("<span style='color:red'>* Enter Material Type</span>");
		return false;
	} else if (mprice == '') {
		$("#mprice").focus();
		$("#err").html("<span style='color:red'>* Enter Material Price</span>");
		return false;
	} else if (!$.isNumeric(mprice)) {
		$("#mprice").focus();
		$("#err").html("<span style='color:red'>* Entered Price invalid .</span>");
		return false;
	} else if (munit == '') {
		$("#err").html("<span style='color:red'>* Enter Material Unit</span>");
		$("#munit").focus();
		return false;
	} else if (!$.isNumeric(munit)) {
		$("#err").html(	"<span style='color:red'>* Entered Unit invalid</span>");
		$("#munit").focus();
		return false;
	}
	return true;
}

function submitDetails(id){
	if(id!=null){
		$("#updatemid").val(id);
		$("#materialForm").submit();
	}else{
		return false;
	}
}

function deleteMaterial(id){
	MyAjax.deleteMaterial(id,{
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
	MyAjax.getPaginatyionMaterial(totalrecord,cpage,limit,{
		async : false,
		callback : function(data) {
			$("#pagelimit").html(data);
		}
	});
}
