
/*$(document).ready(function(){
    $('#name').focus(); 
    $('#mform').click(function(){
        var error_message = $('.err'); 
        if ($('#mname').val() == '') { 
        	$('#mname').focus();
            error_message.html('Material Name is required!');
            return false;
        }
        else {
            return false;
        }
    });
});
*/

function generateApplicantStatusPDF()
{
	$('#loadingDiv').show();
	/*UserStatusAjax.generateEntityFileTypePDF({
		async: true,
		callback: function(data){	
			alert("data :: "+data)
			$('#loadingDiv').hide();	
		//	document.getElementById('ifrmCJS').src = ""+data+"";
			return false;
	     }
	});*/
	
	UserStatusAjax.showMsg({
		async: true,
		callback: function(data){	
			alert("data :: "+data)
			$('#loadingDiv').hide();	
		//	document.getElementById('ifrmCJS').src = ""+data+"";
			return false;
	     }
	})
	
}


function generateApplicantStatusPrintPre()
{
	$('#loadingDiv').fadeIn();
	ApplicantStatusAjax.displayRecordsByEntityTypePrintPreview(endfromDate,endtoDate,jobOrderId,f_searchTextId,f_schoolId,jobStatus,noOfRows,page,sortOrderStr,sortOrderType,{
		async: true,
		callback: function(data)
		{	
			$('#loadingDiv').hide();
			$('#printApplicantStatusDataTableDiv').html(data);
			$("#printApplicantStatusDiv").modal('show');
		}
	});
} 

