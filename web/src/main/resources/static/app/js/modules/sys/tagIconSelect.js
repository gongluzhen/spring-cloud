$(document).ready(function() {
	$("#icons li").click(function() {
		$("#icons li").removeClass("active");
		$("#icons li i").removeClass("icon-white");
		$(this).addClass("active");
		$(this).children("i").addClass("icon-white");
		$("#icon").val($(this).text());
	});
	$("#icons li").each(function() {
		if ($(this).text() == "${value}") {
			$(this).click();
		}
	});
	$("#icons li").dblclick(function() {
		top.$.jBox.getBox().find("button[value='ok']").trigger("click");
	});
});