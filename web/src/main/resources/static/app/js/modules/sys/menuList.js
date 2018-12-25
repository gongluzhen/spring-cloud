$(document).ready(function() {
	$("#treeTable").treeTable({expandLevel : 1}).show();
});
function updateSort() {
	loading('正在提交，请稍等...');
	$("#listForm").attr("action", ctx + "/sys/menu/updateSort");
	$("#listForm").submit();
}