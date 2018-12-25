/*!
 * Copyright &copy; 2012-2016 <a href="https://github.com/sapit/businessIntegration">businessIntegration</a> All rights reserved.
 * 
 * 通用公共方法
 * @author sapit
 * @version 2014-4-29
 */
$(document).ready(function() {
	try{
		// 链接去掉虚框
		$("a").bind("focus",function() {
			if(this.blur) {this.blur()};
		});
		// 所有下拉框使用select2
		$("select").select2({
			language: 'zh-CN'
		});
		$("table").addClass("table-hover");
	}catch(e){
		// blank
	}
});

// 引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}

// 获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

// 恢复提示框显示
function resetTip(){
	$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	$.jBox.closeTip();
}

// 显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在提交，请稍等...";
	}
	resetTip();
	$.jBox.tip(mess,'loading',{
		opacity:0.4,
		persistent: true
	});
}

// 关闭提示框
function closeLoading(){
	// 恢复提示框显示
	resetTip();
	// 关闭提示框
	closeTip();
}

// 警告对话框
function alertx(mess, closed){
	$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	$('.jbox-body .jbox-icon').css('top','55px');
}

// 确认对话框
function confirmx(mess, href, closed){
	$.jBox.confirm(mess,'系统提示',function(v,h,f){
		if(v=='ok'){
			if (typeof href == 'function') {
				href();
			}else{
				resetTip(); // loading();
				loading();
				location = href;
			}
		}
	},{buttonsFocus:1, closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	$('.jbox-body .jbox-icon').css('top','55px');
	return false;
}

// 提示输入对话框
function promptx(title, lable, href, closed){
	$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable + "：<input type='text' id='txt' name='txt'/></div>", {
			title: title, submit: function (v, h, f){
	    if (f.txt == '') {
	        $.jBox.tip("请输入" + lable + "。", 'error');
	        return false;
	    }
		if (typeof href == 'function') {
			href();
		}else{
			resetTip(); // loading();
			location = href + encodeURIComponent(f.txt);
		}
	},closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	return false;
}

// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: title,
        closeable: closeable == undefined,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh != undefined);
}

// cookie操作
function cookie(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires
															// attribute,
															// max-age is not
															// supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
}

// 数值前补零
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){  
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);  
}

// 截取字符串，区别汉字和英文
function abbr(name, maxLength){  
 if(!maxLength){  
     maxLength = 20;  
 }  
 if(name==null||name.length<1){  
     return "";  
 }  
 var w = 0;// 字符串长度，一个汉字长度为2
 var s = 0;// 汉字个数
 var p = false;// 判断字符串当前循环的前一个字符是否为汉字
 var b = false;// 判断字符串当前循环的字符是否为汉字
 var nameSub;  
 for (var i=0; i<name.length; i++) {  
    if(i>1 && b==false){  
         p = false;  
    }  
    if(i>1 && b==true){  
         p = true;  
    }  
    var c = name.charCodeAt(i);  
    // 单字节加1
    if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
         w++;  
         b = false;  
    }else {  
         w+=2;  
         s++;  
         b = true;  
    }  
    if(w>maxLength && i<=name.length-1){  
         if(b==true && p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(b==false && p==false){  
             nameSub = name.substring(0,i-3)+"...";  
         }  
         if(b==true && p==false){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         break;  
    }  
 }  
 if(w<=maxLength){  
     return name;  
 }  
 return nameSub;  
}

$(function(){
    // 判断整数value是否等于0
    jQuery.validator.addMethod("isIntEqZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value==0;       
    }, "整数必须为0"); 
      
    // 判断整数value是否大于0
    jQuery.validator.addMethod("isIntGtZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value>0;       
    }, "整数必须大于0"); 
      
    // 判断整数value是否大于或等于0
    jQuery.validator.addMethod("isIntGteZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value>=0;       
    }, "整数必须大于或等于0");   
    
    // 判断整数value是否不等于0
    jQuery.validator.addMethod("isIntNEqZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value!=0;       
    }, "整数必须不等于0");  
    
    // 判断整数value是否小于0
    jQuery.validator.addMethod("isIntLtZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value<0;       
    }, "整数必须小于0");  
    
    // 判断整数value是否小于或等于0
    jQuery.validator.addMethod("isIntLteZero", function(value, element) { 
         value=parseInt(value);      
         return this.optional(element) || value<=0;       
    }, "整数必须小于或等于0");  
    
    // 判断浮点数value是否等于0
    jQuery.validator.addMethod("isFloatEqZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value==0;       
    }, "浮点数必须为0"); 
      
    // 判断浮点数value是否大于0
    jQuery.validator.addMethod("isFloatGtZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value>0;       
    }, "浮点数必须大于0"); 
      
    // 判断浮点数value是否大于或等于0
    jQuery.validator.addMethod("isFloatGteZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value>=0;       
    }, "浮点数必须大于或等于0");   
    
    // 判断浮点数value是否不等于0
    jQuery.validator.addMethod("isFloatNEqZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value!=0;       
    }, "浮点数必须不等于0");  
    
    // 判断浮点数value是否小于0
    jQuery.validator.addMethod("isFloatLtZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value<0;       
    }, "浮点数必须小于0");  
    
    // 判断浮点数value是否小于或等于0
    jQuery.validator.addMethod("isFloatLteZero", function(value, element) { 
         value=parseFloat(value);      
         return this.optional(element) || value<=0;       
    }, "浮点数必须小于或等于0");  
    
    // 判断浮点型
    jQuery.validator.addMethod("isFloat", function(value, element) {       
         return this.optional(element) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
    }, "只能包含数字、小数点等字符"); 
     
    // 匹配integer
    jQuery.validator.addMethod("isInteger", function(value, element) {       
         return this.optional(element) || (/^[-\+]?\d+$/.test(value) && parseInt(value)>=0);       
    }, "匹配integer");  
     
    // 判断数值类型，包括整数和浮点数
    jQuery.validator.addMethod("isNumber", function(value, element) {       
         return this.optional(element) || /^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
    }, "匹配数值类型，包括整数和浮点数");  
    
    // 只能输入[0-9]数字
    jQuery.validator.addMethod("isDigits", function(value, element) {       
         return this.optional(element) || /^\d+$/.test(value);       
    }, "只能输入0-9数字");  
    
    // 判断中文字符
    jQuery.validator.addMethod("isChinese", function(value, element) {       
         return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
    }, "只能包含中文字符。");   
 
    // 判断英文字符
    jQuery.validator.addMethod("isEnglish", function(value, element) {       
         return this.optional(element) || /^[A-Za-z]+$/.test(value);       
    }, "只能包含英文字符。");   
 
     // 手机号码验证
    jQuery.validator.addMethod("isMobile", function(value, element) {    
      var length = value.length;    
      return this.optional(element) || (length == 11 && /^\d{11}$/.test(value));    
    }, "请正确填写您的手机号码。");

    // 电话号码验证
    jQuery.validator.addMethod("isPhone", function(value, element) {    
      var tel = /^(\d{3,4}-?)?\d{7,9}$/g;    
      return this.optional(element) || (tel.test(value));    
    }, "请正确填写您的电话号码。");

    // 联系电话(手机/电话皆可)验证
    jQuery.validator.addMethod("isTel", function(value,element) {   
        var length = value.length;   
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
    }, "请正确填写您的联系方式"); 
 
     // 匹配qq
    jQuery.validator.addMethod("isQq", function(value, element) {       
         return this.optional(element) || /^[1-9]\d{4,12}$/;       
    }, "匹配QQ");   
 
     // 邮政编码验证
    jQuery.validator.addMethod("isZipCode", function(value, element) {    
      var zip = /^[0-9]{6}$/;    
      return this.optional(element) || (zip.test(value));    
    }, "请正确填写您的邮政编码。");  
    
    // 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
    jQuery.validator.addMethod("isPwd", function(value, element) {       
         return this.optional(element) || /^[a-zA-Z]\\w{6,12}$/.test(value);       
    }, "以字母开头，长度在6-12之间，只能包含字符、数字和下划线。");  
    
    jQuery.validator.addMethod("isLoginName", function(value, element) {
         return this.optional(element) || /^([a-zA-Z]*)(-)([0-9X]{4})$/.test(value);       
    }, "登录名规则为：姓名拼音+中横线+身份证后四位。");  
    
    // 身份证号码验证
    jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
      // var idCard = /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/;
      return this.optional(element) || isIdCardNo(value);    
    }, "请输入正确的身份证号码。"); 
    
    jQuery.validator.addMethod("decimal", function(value, element) {
    	var decimal = /^(\d{1,12})(\.\d{1,4})?$/;
    	return this.optional(element) || (decimal.test(value));
	}, $.validator.format("请输入正确的数值 ，例如：1.1234。"));

    // IP地址验证
    jQuery.validator.addMethod("ip", function(value, element) {    
      return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
    }, "请填写正确的IP地址。");
   
    // 字符验证，只能包含中文、英文、数字、下划线等字符。
    jQuery.validator.addMethod("stringCheck", function(value, element) {       
         return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/.test(value);       
    }, "只能包含中文、英文、数字、下划线等字符");   
   
    // 匹配english
    jQuery.validator.addMethod("isEnglish", function(value, element) {       
         return this.optional(element) || /^[A-Za-z]+$/.test(value);       
    }, "匹配english");   
    
    // 匹配汉字
    jQuery.validator.addMethod("isChinese", function(value, element) {       
         return this.optional(element) || /^[\u4e00-\u9fa5]+$/.test(value);       
    }, "匹配汉字");   
    
    // 匹配中文(包括汉字和字符)
    jQuery.validator.addMethod("isChineseChar", function(value, element) {       
         return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
    }, "匹配中文(包括汉字和字符) "); 
      
    // 判断是否为合法字符(a-zA-Z0-9-_)
    jQuery.validator.addMethod("isRightfulString", function(value, element) {       
         return this.optional(element) || /^[A-Za-z0-9_-]+$/.test(value);       
    }, "判断是否为合法字符(a-zA-Z0-9-_)");   
    
    // 判断是否包含中英文特殊字符，除英文"-_"字符外
    jQuery.validator.addMethod("isContainsSpecialChar", function(value, element) {  
         var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
         return this.optional(element) || !reg.test(value);       
    }, "含有中英文特殊字符");   
   

    // 身份证号码的验证规则
    function isIdCardNo(num){ 
    　   // if (isNaN(num)) {alert("输入的不是数字！"); return false;}
    　　 var len = num.length, re; 
    　　 if (len == 15) 
    　　 re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/); 
    　　 else if (len == 18) 
    　　 re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/); 
    　　 else {
            // alert("输入的数字位数不对。");
            return false;
        } 
    　　 var a = num.match(re); 
    　　 if (a != null) 
    　　 { 
    　　 if (len==15) 
    　　 { 
    　　 var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]); 
    　　 var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
    　　 } 
    　　 else 
    　　 { 
    　　 var D = new Date(a[3]+"/"+a[4]+"/"+a[5]); 
    　　 var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
    　　 } 
    　　 if (!B) {
            // alert("输入的身份证号 "+ a[0] +" 里出生日期不对。");
            return false;
        } 
    　　 } 
    　　 if(!re.test(num)){
            // alert("身份证最后一位只能是数字和字母。");
            return false;
        }
    　　 return true; 
    	
    	
// num = num.replace(/\s/g,"");
// var len = num.length;
// if (len == 8) {
// // 因公普通的是:P+7位数 ,公务的是：S+7位数
// if(/([P|p|S|s]\d{7})$/.test(num)){
// return true;
// }
// // 澳门身份证或不带（）的香港居民身份证
// return IdentityAMCodeValid(num)||IdentityHKCodeValid(num);
// }else if(len == 9){
// // 因私普通护照号码格式有:14/15+7位数,G+8位数.公务的是： S+8位数，电子芯片的普通护照：E+8位数
// if(/^1[45][0-9]{7}|([S|s|G|g]\d{8})|([E|e]\d{8})|^\d{9}$/.test(num)){
// return true;
// }
// return false;
// } else if (len == 10) {
// if(/([Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\d{8})$/.test(num)){
// return true;
// }
// // 香港或台湾身份证
// if (num.indexOf("(") > 0||code.indexOf("（") >0) {
// // 香港身份证
// return IdentityHKCodeValid(num);
// } else {
// // 台湾身份证
// return IdentityTWCodeValid(num);
// }
// } else if (len == 11){
// // H:香港特区护照和香港公民所持回乡卡H开头,后接10位数字;M:澳门特区护照和澳门公民所持回乡卡M开头,后接10位数字
// if(/([H|h|M|m]\d{8,10})$/.test(num)){
// return true;
// }
// return false;
// }else if (len == 18) {// 大陆身份证
// return IdentityCNCodeValid(num);
// }else {// 一定不正确
// return false;
// }
    } 

});

var DateUtil = function(){
	 
    /**
	 * 判断闰年
	 * 
	 * @param date
	 *            Date日期对象
	 * @return boolean true 或false
	 */
    this.isLeapYear = function(date){
        return (0==date.getYear()%4&&((date.getYear()%100!=0)||(date.getYear()%400==0))); 
    }
     
    /**
	 * 日期对象转换为指定格式的字符串
	 * 
	 * @param f
	 *            日期格式,格式定义如下 yyyy-MM-dd HH:mm:ss
	 * @param date
	 *            Date日期对象, 如果缺省，则为当前时间
	 * 
	 * YYYY/yyyy/YY/yy 表示年份 MM/M 月份 W/w 星期 dd/DD/d/D 日期 hh/HH/h/H 时间 mm/m 分钟
	 * ss/SS/s/S 秒
	 * @return string 指定格式的时间字符串
	 */
    this.dateToStr = function(formatStr, date){
        formatStr = arguments[0] || "yyyy-MM-dd HH:mm:ss";
        date = arguments[1] || new Date();
        var str = formatStr;   
        var Week = ['日','一','二','三','四','五','六'];  
        str=str.replace(/yyyy|YYYY/,date.getFullYear());   
        str=str.replace(/yy|YY/,(date.getYear() % 100)>9?(date.getYear() % 100).toString():'0' + (date.getYear() % 100));   
        str=str.replace(/MM/,date.getMonth()>9?(date.getMonth() + 1):'0' + (date.getMonth() + 1));   
        str=str.replace(/M/g,date.getMonth());   
        str=str.replace(/w|W/g,Week[date.getDay()]);   
       
        str=str.replace(/dd|DD/,date.getDate()>9?date.getDate().toString():'0' + date.getDate());   
        str=str.replace(/d|D/g,date.getDate());   
       
        str=str.replace(/hh|HH/,date.getHours()>9?date.getHours().toString():'0' + date.getHours());   
        str=str.replace(/h|H/g,date.getHours());   
        str=str.replace(/mm/,date.getMinutes()>9?date.getMinutes().toString():'0' + date.getMinutes());   
        str=str.replace(/m/g,date.getMinutes());   
       
        str=str.replace(/ss|SS/,date.getSeconds()>9?date.getSeconds().toString():'0' + date.getSeconds());   
        str=str.replace(/s|S/g,date.getSeconds());   
       
        return str;   
    }
 
     
    /**
	 * 日期计算
	 * 
	 * @param strInterval
	 *            string 可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 * @param num
	 *            int
	 * @param date
	 *            Date 日期对象
	 * @return Date 返回日期对象
	 */
    this.dateAdd = function(strInterval, num, date){
        date =  arguments[2] || new Date();
        switch (strInterval) { 
            case 's' :return new Date(date.getTime() + (1000 * num));  
            case 'n' :return new Date(date.getTime() + (60000 * num));  
            case 'h' :return new Date(date.getTime() + (3600000 * num));  
            case 'd' :return new Date(date.getTime() + (86400000 * num));  
            case 'w' :return new Date(date.getTime() + ((86400000 * 7) * num));  
            case 'm' :return new Date(date.getFullYear(), (date.getMonth()) + num, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());  
            case 'y' :return new Date((date.getFullYear() + num), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());  
        }  
    }  
     
    /**
	 * 比较日期差 dtEnd 格式为日期型或者有效日期格式字符串
	 * 
	 * @param strInterval
	 *            string 可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 * @param dtStart
	 *            Date 可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 * @param dtEnd
	 *            Date 可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 */
    this.dateDiff = function(strInterval, dtStart, dtEnd) {   
        switch (strInterval) {   
            case 's' :return parseInt((dtEnd - dtStart) / 1000);  
            case 'n' :return parseInt((dtEnd - dtStart) / 60000);  
            case 'h' :return parseInt((dtEnd - dtStart) / 3600000);  
            case 'd' :return parseInt((dtEnd - dtStart) / 86400000);  
            case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));  
            case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);  
            case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();  
        }  
    }
 
    /**
	 * 字符串转换为日期对象
	 * 
	 * @param date
	 *            Date 格式为yyyy-MM-dd HH:mm:ss，必须按年月日时分秒的顺序，中间分隔符不限制
	 */
    this.strToDate = function(dateStr){
        var data = dateStr;  
        var reCat = /(\d{1,4})/gm;   
        var t = data.match(reCat);
        t[1] = t[1] - 1;
        eval('var d = new Date('+t.join(',')+');');
        return d;
    }
 
    /**
	 * 把指定格式的字符串转换为日期对象yyyy-MM-dd HH:mm:ss
	 * 
	 */
    this.strFormatToDate = function(formatStr, dateStr){
        var year = 0;
        var start = -1;
        var len = dateStr.length;
        if((start = formatStr.indexOf('yyyy')) > -1 && start < len){
            year = dateStr.substr(start, 4);
        }
        var month = 0;
        if((start = formatStr.indexOf('MM')) > -1  && start < len){
            month = parseInt(dateStr.substr(start, 2)) - 1;
        }
        var day = 0;
        if((start = formatStr.indexOf('dd')) > -1 && start < len){
            day = parseInt(dateStr.substr(start, 2));
        }
        var hour = 0;
        if( ((start = formatStr.indexOf('HH')) > -1 || (start = formatStr.indexOf('hh')) > 1) && start < len){
            hour = parseInt(dateStr.substr(start, 2));
        }
        var minute = 0;
        if((start = formatStr.indexOf('mm')) > -1  && start < len){
            minute = dateStr.substr(start, 2);
        }
        var second = 0;
        if((start = formatStr.indexOf('ss')) > -1  && start < len){
            second = dateStr.substr(start, 2);
        }
        return new Date(year, month, day, hour, minute, second);
    }
 
 
    /**
	 * 日期对象转换为毫秒数
	 */
    this.dateToLong = function(date){
        return date.getTime();
    }
 
    /**
	 * 毫秒转换为日期对象
	 * 
	 * @param dateVal
	 *            number 日期的毫秒数
	 */
    this.longToDate = function(dateVal){
        return new Date(dateVal);
    }
 
    /**
	 * 判断字符串是否为日期格式
	 * 
	 * @param str
	 *            string 字符串
	 * @param formatStr
	 *            string 日期格式， 如下 yyyy-MM-dd
	 */
    this.isDate = function(str, formatStr){
        if (formatStr == null){
            formatStr = "yyyyMMdd";    
        }
        var yIndex = formatStr.indexOf("yyyy");     
        if(yIndex==-1){
            return false;
        }
        var year = str.substring(yIndex,yIndex+4);     
        var mIndex = formatStr.indexOf("MM");     
        if(mIndex==-1){
            return false;
        }
        var month = str.substring(mIndex,mIndex+2);     
        var dIndex = formatStr.indexOf("dd");     
        if(dIndex==-1){
            return false;
        }
        var day = str.substring(dIndex,dIndex+2);     
        if(!isNumber(year)||year>"2100" || year< "1900"){
            return false;
        }
        if(!isNumber(month)||month>"12" || month< "01"){
            return false;
        }
        if(day>getMaxDay(year,month) || day< "01"){
            return false;
        }
        return true;   
    }
     
    this.getMaxDay = function(year,month) {     
        if(month==4||month==6||month==9||month==11)     
            return "30";     
        if(month==2)     
            if(year%4==0&&year%100!=0 || year%400==0)     
                return "29";     
            else    
                return "28";     
        return "31";     
    }     
    /**
	 * 变量是否为数字
	 */
    this.isNumber = function(str)
    {
        var regExp = /^\d+$/g;
        return regExp.test(str);
    }
     
    /**
	 * 把日期分割成数组 [年、月、日、时、分、秒]
	 */
    this.toArray = function(myDate)  
    {   
        myDate = arguments[0] || new Date();
        var myArray = Array();  
        myArray[0] = myDate.getFullYear();  
        myArray[1] = myDate.getMonth();  
        myArray[2] = myDate.getDate();  
        myArray[3] = myDate.getHours();  
        myArray[4] = myDate.getMinutes();  
        myArray[5] = myDate.getSeconds();  
        return myArray;  
    }  
     
    /**
	 * 取得日期数据信息 参数 interval 表示数据类型 y 年 M月 d日 w星期 ww周 h时 n分 s秒
	 */
    this.datePart = function(interval, myDate)  
    {   
        myDate = arguments[1] || new Date();
        var partStr='';  
        var Week = ['日','一','二','三','四','五','六'];  
        switch (interval)  
        {   
            case 'y' :partStr = myDate.getFullYear();break;  
            case 'M' :partStr = myDate.getMonth()+1;break;  
            case 'd' :partStr = myDate.getDate();break;  
            case 'w' :partStr = Week[myDate.getDay()];break;  
            case 'ww' :partStr = myDate.WeekNumOfYear();break;  
            case 'h' :partStr = myDate.getHours();break;  
            case 'm' :partStr = myDate.getMinutes();break;  
            case 's' :partStr = myDate.getSeconds();break;  
        }  
        return partStr;  
    }  
     
    /**
	 * 取得当前日期所在月的最大天数
	 */
    this.maxDayOfDate = function(date)  
    {   
        date = arguments[0] || new Date();
        date.setDate(1);
        date.setMonth(date.getMonth() + 1);
        var time = date.getTime() - 24 * 60 * 60 * 1000;
        var newDate = new Date(time);
        return newDate.getDate();
    }
     
    return this;
}();

Array.prototype.unique3 = function(){
	var res = [];
	var json = {};
	for(var i = 0; i < this.length; i++){
		if(!json[this[i]]){
			res.push(this[i]);
			json[this[i]] = 1;
		}
	}
	return res;
}

function setExportTimeOut(obj, countdown) { 
	if (countdown == 0) { 
        obj.removeAttribute("disabled");    
        obj.value="导出"; 
        countdown = 5; 
        return;
    } else { 
        obj.setAttribute("disabled", true); 
        obj.value="导出(" + countdown + ")"; 
        countdown--; 
    } 
	setTimeout(function() {setExportTimeOut(obj, countdown)} ,1000);
}

function toPercent(point){
	if (point == null){
		point = "0";
    }
    var str=Number(point*100).toFixed(4);
    str+="%";
    return str;
}

Array.prototype.indexOf = function(val) { 
	for (var i = 0; i < this.length; i++) { 
		if (this[i] == val) return i; 
	} 
	return -1; 
};
Array.prototype.remove = function(val) { 
	var index = this.indexOf(val); 
	if (index > -1) { 
		this.splice(index, 1); 
	} 
};
