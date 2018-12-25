$(document).ready(function() {
    $('#uploadFile').bootstrapFileInput();
    $("#btnExport").click(function(){
        var obj = this;
        top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
            if(v=="ok"){
                setExportTimeOut(obj, 10);
                $("#searchForm").attr("action", ctx + "sys/user/export");
                $("#searchForm").submit();
            }
        },{buttonsFocus:1});
        top.$('.jbox-body .jbox-icon').css('top','55px');
    });
    $("#btnImport").click(function(){
        $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
    });
});
function page(n,s){
    if(n) $("#pageNo").val(n);
    if(s) $("#pageSize").val(s);
    $("#searchForm").attr("action", ctx + "sys/user/list");
    $("#searchForm").submit();
    return false;
}

//查看详情
function seeDetails(id) {
    top.$.jBox.open("iframe:" + ctx + "sys/user/userDetails?id=" + id, "用户详情", 800, $(top.document).height() - 150, {
        buttons: {"关闭": true}
    });
}

/**
 * 清空查询的内容
 */
function clearSearchForm() {
    $("#loginName").val("");    //登录名
    $("#officeId").val("");     //归属部门
    $("#officeName").val("");   //归属部门
    $("#name").val("");         //姓名
}