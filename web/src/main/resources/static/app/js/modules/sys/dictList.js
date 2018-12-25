function page(n, s) {
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}

/**
 * 清空查询的内容
 */
function clearSearchForm() {
	$("#type").val("").trigger("change");// 类型
	$("#description").val(""); // 描述
}

function typeClick(type){
	$('#type').val(type);
	$('#searchForm').submit();
	return false;
}