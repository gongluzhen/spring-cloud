$(document).ready(function() {
	$("#no").focus();

	$("#inputForm").validate({
		rules: {
			loginName: {remote: ctx + "sys/user/checkLoginName?oldLoginName=" + encodeURIComponent($("#oldLoginName").val()) + '&t=' + new Date().getTime()}
		},
		messages: {
			loginName: {remote: "用户登录名已存在"},
			confirmNewPassword: {equalTo: "输入与上面相同的密码"}
		},
		submitHandler: function(form){
			loading('正在提交，请稍等...');
			$("#btnSubmit").attr("disabled", true);
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