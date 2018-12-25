$(document).ready(function() {
	$("#comments").focus();
	$("#inputForm").validate({
		submitHandler: function(form){
			loading('正在提交，请稍等...');
			$("input[type=checkbox]").each(function(){
				$(this).after("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""
						+($(this).attr("checked")?"1":"0")+"\"/>");
				$(this).attr("name", "_"+$(this).attr("name"));
			});
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