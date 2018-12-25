var setting = {
	data : {
		simpleData : {
			enable : true,
			idKey : "id",
			pIdKey : "pId",
			rootPId : '0'
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			var id = treeNode.pId == '0' ? '' : treeNode.pId;
			$('#officeContent').attr("src", ctx + "sys/office/list?id=" + id + "&parentIds=" + treeNode.pIds);
		}
	}
};

function refreshTree() {
	$.getJSON(ctx + "sys/office/treeData?t=" + new Date().getTime(), function(data) {
		$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
	});
}
refreshTree();

var leftWidth = 180; // 左侧窗口大小
var htmlObj = $("html"), mainObj = $("#main");
var frameObj = $("#left, #openClose, #right, #right iframe");
function wSize() {
	var strs = getWindowSize().toString().split(",");
	htmlObj.css({
		"overflow-x" : "hidden",
		"overflow-y" : "hidden"
	});
	mainObj.css("width", "auto");
	frameObj.height(strs[0] - 5);
	var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
	$("#right").width(
			$("#content").width() - leftWidth - $("#openClose").width() - 5);
	$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
}