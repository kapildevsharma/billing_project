function getAllUserList() {
//	alert("Ok..")
	MyAjax.getUserList({
		async : false,
		callback : function(data) {
			console.log(data);
		}
	});
}

$(document).ready(function() {
	getAllUserList();
});