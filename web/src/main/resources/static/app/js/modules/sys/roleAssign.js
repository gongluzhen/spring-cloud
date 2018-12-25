$(document).ready(function(){
	$("#assignButton").click(function(){
		top.$.jBox.open("iframe:" + ctx + "sys/role/usertorole?id=" + $("#id").val(), "分配角色",810,$(top.document).height()-240,{
			buttons:{"确定分配":"ok", "关闭":true}, bottomText:"通过选择部门，然后为列出的人员分配角色。",submit:function(v, h, f){
				var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
				var ids = h.find("iframe")[0].contentWindow.ids;
				if (v=="ok"){
					// 删除''的元素
					if(ids[0]==''){
						ids.shift();
						pre_ids.shift();
					}
					if(pre_ids.sort().toString() == ids.sort().toString()){
						top.$.jBox.tip("未给角色【" + $("#role-name").val() + "】分配新成员！", 'info');
						return false;
					};
			    	// 执行保存
			    	loading('正在提交，请稍等...');
			    	var idsArr = "";
			    	for (var i = 0; i<ids.length; i++) {
			    		idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
			    	}
			    	$('#idsArr').val(idsArr);
			    	$('#assignRoleForm').submit();
			    	return true;
				}
			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	});
});