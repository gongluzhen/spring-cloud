$(document).ready(function() {
	$("#oldPassword").focus();
	$("#inputForm").validate({
		rules: {
		},
		messages: {
			confirmNewPassword: {equalTo: "输入与上面相同的密码"}
		},
		submitHandler: function(form){
            $("#btnSubmit").attr("disabled", true);
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
});