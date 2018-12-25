$(document).ready(function() {
	var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	var data = eval('(' + $("#list").val() + ')'), rootId = $("#rootId").val();
	addRow("#treeTableList", tpl, data, rootId, true);
	$("#treeTable").treeTable({expandLevel : 5});
	
	if($("#refreshTree").val() == 'true'){
		parent.refreshTree();	
	}
});
function addRow(list, tpl, data, pid, root){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if ((row ? row.parentId : '') == pid){
			$(list).append(Mustache.render(tpl, {
				pid: (root ? 0 : pid),
				row: row,
				ctx: ctx
			}));
			addRow(list, tpl, data, row.id);
		}
	}
}