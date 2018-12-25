$(document).ready(function() {

});
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}

function queryParentTable(parentTable){
	$('#parentTable').val(parentTable);
	$('#searchForm').submit();
}