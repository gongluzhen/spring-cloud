var leftWidth = 160; // 左侧窗口大小
var tabTitleHeight = 33; // 页签的高度
var htmlObj = $("html"), mainObj = $("#main");
var headerObj = $("#header"), footerObj = $("#footer");
var frameObj = $("#left, #openClose, #right, #right iframe");
function wSize(){
	var minHeight = 500, minWidth = 980;
	var strs = getWindowSize().toString().split(",");
	htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":strs[0] < minHeight ? "auto" : "hidden"});
	mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
	frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
	$("#openClose").height($("#openClose").height() - 5);
	$(".jericho_tab iframe").height($("#right").height() - tabTitleHeight);
	wSizeWidth();
}
function wSizeWidth(){
	if (!$("#openClose").is(":hidden")){
		var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
		$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
	}else{
		$("#right").width("100%");
	}
}
function openCloseClickCallBack(b){
	if($.fn.jerichoTab){
		$.fn.jerichoTab.resize();
	}
}

$(document).ready(function() {
	if(cookie('tabmode') && cookie('tabmode') == '1'){
		$.fn.initJerichoTab({
	        renderTo: '#right', uniqueId: 'jerichotab',
	        contentCss: { 'height': $('#right').height() - tabTitleHeight },
	        tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
	    });
	}
	// 绑定菜单单击事件
	$("#menu a.menu").click(function(){
		// 一级菜单焦点
		$("#menu li.menu").removeClass("active");
		$(this).parent().addClass("active");
		// 左侧区域隐藏
		if ($(this).attr("target") == "mainFrame"){
			$("#left,#openClose").hide();
			wSizeWidth();
			
			if(cookie('tabmode') && cookie('tabmode') == '1'){
				$(".jericho_tab").hide();
				$("#mainFrame").show();
			}
			return true;
		}
		// 左侧区域显示
		$("#left,#openClose").show();
		if(!$("#openClose").hasClass("close")){
			$("#openClose").click();
		}
		// 显示二级菜单
		var menuId = "#menu-" + $(this).attr("data-id");
		if ($(menuId).length > 0){
			$("#left .accordion").hide();
			$(menuId).show();
			// 初始化点击第一个二级菜单
			if (!$(menuId + " .accordion-body:first").hasClass('in')){
				$(menuId + " .accordion-heading:first a").click();
			}
			if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")){
				$(menuId + " .accordion-body a:first i").click();
			}
			// 初始化点击第一个三级菜单
			$(menuId + " .accordion-body li:first li:first a:first i").click();
		}else{
			// 获取二级菜单数据
			$.get($(this).attr("data-href") + "&t=" + new Date().getTime(), function(data){
				if (data.indexOf("id=\"loginForm\"") != -1){
					alert('未登录或登录超时。请重新登录，谢谢！');
					top.location = "${ctx}";
					return false;
				}
				$("#left .accordion").hide();
				$("#left").append(data);
				// 链接去掉虚框
				$(menuId + " a").bind("focus",function() {
					if(this.blur) {this.blur()};
				});
				// 二级标题
				$(menuId + " .accordion-heading a").click(function(){
					$(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
					if(!$($(this).attr('data-href')).hasClass('in')){
						$(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
					}
				});
				// 二级内容
				$(menuId + " .accordion-body a").click(function(){
					$(menuId + " li").removeClass("active");
					$(menuId + " li i").removeClass("icon-white");
					$(this).parent().addClass("active");
					$(this).children("i").addClass("icon-white");
				});
				// 展现三级
				$(menuId + " .accordion-inner a").click(function(){
					var href = $(this).attr("data-href");
					if($(href).length > 0){
						$(href).toggle().parent().toggle();
						return false;
					}
					if(cookie('tabmode') && cookie('tabmode') == '1'){
						return addTab($(this));
					}
				});
				// 默认选中第一个菜单
				$(menuId + " .accordion-body a:first i").click();
				$(menuId + " .accordion-body li:first li:first a:first i").click();
			});
		}
		// 大小宽度调整
		wSizeWidth();
		return false;
	});
	// 初始化点击第一个一级菜单
	$("#menu a.menu:first span").click();

	if(cookie('tabmode') && cookie('tabmode') == '1'){
		$("#userInfo .dropdown-menu a").mouseup(function(){
			return addTab($(this), true);
		});
	}
	// 鼠标移动到边界自动弹出左侧菜单
	$("#openClose").mouseover(function(){
		if($(this).hasClass("open")){
			$(this).click();
		}
	});
});
function addTab($this, refresh){
	$(".jericho_tab").show();
	$("#mainFrame").hide();
	$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: $this.text(),
        closeable: true,
        data: {
            dataType: 'iframe',
            dataLink: $this.attr('href')
        }
    }).loadData(refresh);
	return false;
}