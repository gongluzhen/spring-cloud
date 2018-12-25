$(document).ready(function(){
	$("#name").focus();
	$("#inputForm").validate({
		rules: {
			name: {remote: ctx + "sys/role/checkName?oldName=" + encodeURIComponent($("#oldName").val())},
			enname: {remote: ctx + "sys/role/checkEnname?oldEnname=" + encodeURIComponent($("#oldEnname").val())}
		},
		messages: {
			name: {remote: "角色名已存在"},
			enname: {remote: "英文名已存在"}
		},
		submitHandler: function(form){
			var ids = [], nodes = tree.getCheckedNodes(true);
			for(var i=0; i<nodes.length; i++) {
				ids.push(nodes[i].id);
			}
			$("#menuIds").val(ids);
			var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
			for(var i=0; i<nodes2.length; i++) {
				ids2.push(nodes2[i].id);
			}
			$("#officeIds").val(ids2);
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

	var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
			data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
				tree.checkNode(node, !node.checked, true, true);
				return false;
			}}};
	
	// 用户-菜单
	var zNodes = [];
	$.each(eval('(' + $("#menuList").val() + ')'), function(i, o){
		zNodes.push({
			id: o.id, 
			pId: (o.parent != null && o.parent.id != null ? o.parent.id : '0'), 
			name: (o.parent != null && o.parent.id != null ? o.name : '权限列表')
		});
	});
	// 初始化树结构
	var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
	// 不选择父节点
	tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
	// 默认选择节点
	var ids = $("#menuIds").val().split(",");
	for(var i=0; i<ids.length; i++) {
		var node = tree.getNodeByParam("id", ids[i]);
		try{tree.checkNode(node, true, false);}catch(e){}
	}
	// 默认展开全部节点
	tree.expandAll(true);
	
	// 用户-机构
	var zNodes2=[];
	$.each(eval('(' + $("#officeList").val() + ')'), function(i, o){
		zNodes2.push({
			id: o.id, 
			pId: (o.parent != null && o.parent.id != null ? o.parent.id : '0'), 
			name: o.name
		});
	});
	// 初始化树结构
	var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
	// 不选择父节点
	tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
	// 默认选择节点
	var ids2 = $("#officeIds").val().split(",");
	for(var i=0; i<ids2.length; i++) {
		var node = tree2.getNodeByParam("id", ids2[i]);
		try{tree2.checkNode(node, true, false);}catch(e){}
	}
	// 默认展开全部节点
	tree2.expandAll(true);
	// 刷新（显示/隐藏）机构
	refreshOfficeTree();
	$("#dataScope").change(function(){
		refreshOfficeTree();
	});
});
function refreshOfficeTree(){
	if($("#dataScope").val()==9){
		$("#officeTree").show();
	}else{
		$("#officeTree").hide();
	}
}