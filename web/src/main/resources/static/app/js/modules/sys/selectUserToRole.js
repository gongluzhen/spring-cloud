var officeTree;
var selectedTree;// zTree已选择对象

// 初始化
$(document).ready(function() {
	officeTree = $.fn.zTree.init($("#officeTree"), setting, officeNodes);
	selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
});

var setting = {
	view : {
		selectedMulti : false,
		nameIsHTML : true,
		showTitle : false,
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onClick : treeOnClick
	}
};

var officeNodes = [];
$.each(eval('(' + $("#officeList").val() + ')'), function(i, o) {
	officeNodes.push({
		id : o.id,
		pId : (o.parent != null && o.parent.id != null ? o.parent.id : '0'),
		name : o.name
	});
});

var pre_selectedNodes = [];
$.each(eval('(' + $("#userList").val() + ')'), function(i, o) {
	pre_selectedNodes.push({
		id : o.id,
		pId : '0',
		name : '<font color="red" style="font-weight:bold;">' + o.name + '</font>'
	});
});

var selectedNodes = [];
$.each(eval('(' + $("#userList").val() + ')'), function(i, o) {
	selectedNodes.push({
		id : o.id,
		pId : '0',
		name : '<font color="red" style="font-weight:bold;">' + o.name + '</font>'
	});
});

var pre_ids = $("#selectIds").val().split(",");
var ids = $("#selectUserIds").val().split(",");

// 点击选择项回调
function treeOnClick(event, treeId, treeNode, clickFlag) {
	$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
	if ("officeTree" == treeId) {
		$.get(ctx + "sys/role/users?officeId=" + treeNode.id + "&t=" + new Date().getTime(), function(userNodes) {
			$.fn.zTree.init($("#userTree"), setting, userNodes);
		});
	}
	if ("userTree" == treeId) {
		if ($.inArray(String(treeNode.id), ids) < 0) {
			selectedTree.addNodes(null, treeNode);
			ids.push(String(treeNode.id));
		}
	}
	;
	if ("selectedTree" == treeId) {
		if ($.inArray(String(treeNode.id), pre_ids) < 0) {
			selectedTree.removeNode(treeNode);
			var ddd = ids.splice($.inArray(String(treeNode.id), ids), 1);
		} else {
			top.$.jBox.tip("角色原有成员不能清除！", 'info');
		}
	}
};
