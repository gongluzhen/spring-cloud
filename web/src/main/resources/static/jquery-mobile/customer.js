/**
 * 自动化 表单数据
 * @param $form
 * @param uuid
 * @param code
 * @param page
 * @param rows
 * @returns {{}}
 */
function ajaxData($form, token, code, page, rows) {
    // 提交数据
    var ajaxData = {}
    // 基础信息
    var headObj = {}
    if (page == null && rows == null) {
        headObj = {
            "tokenId": token,
            "interfaceCode": code
        };
    } else {
        headObj = {
            "tokenId": token,
            "interfaceCode": code,
            "page": page,
            "rows": rows
        };
    }
    // 表单数据
    var serializeObj = {};
    var array = $form.serializeArray();
    var str = $form.serialize();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    ajaxData["head"] = headObj;
    ajaxData["body"] = serializeObj;
    return ajaxData;
}
/**
 * 手动 传
 * @param $form
 * @param uuid
 * @param code
 * @param page
 * @param rows
 * @returns {{}}
 */
function ajaxCusData(arr, token, code, page, rows) {
    // 提交数据
    var ajaxData = {}
    // 基础信息
    var headObj = {}
    if (page == null && rows == null) {
        headObj = {
            "tokenId": token,
            "interfaceCode": code
        };
    } else {
        headObj = {
            "tokenId": token,
            "interfaceCode": code,
            "page": page,
            "rows": rows
        };
    }
    // 表单数据
    var serializeObj = {};
    $(arr).each(function () {
        serializeObj[this.name] = this.value;
    });
    ajaxData["head"] = headObj;
    ajaxData["body"] = serializeObj;
    return ajaxData;
}
/**
 * 弹窗信息
 * @param msg
 */
function alertMsg(msg) {
    layer.closeAll();

    layer.open({
       content: msg
       ,skin: 'msg'
       ,time: 2 //2秒后自动关闭
   });
}

// 保存本地数据
var localDataItme = sessionStorage;
/**
 * 保存
 * @param key
 * @param value
 */
function localSetData(key, value) {
    try {
        $.cookie(key, value);
        // localDataItme.setItem(key, value);
    } catch (e) {
        // if (e.name == 'QuotaExceededError') {
        //     alertMsg("超出本地存储限额！")
        // }
    }
}
/**
 * 获取
 * @param key
 * @returns {string}
 */
function localGetData(key) {
    try {
        var val = $.cookie(key);
        //var val = localDataItme.getItem(key);
        if ("undefined" == val || val == null) {
            val = "";
        }
        return val;
    } catch (e) {
        return "";
    }
}
/**
 * 删除
 * @param key
 */
// function localRemoveData(key) {
//     localClearData.removeItem(key);
// }

function checkValue(_this,arr){
    $(_this).parent().nextAll(".showMsgSpan").remove();
    var bool = true;
    var val = _this.value;
    if(val.length <= 0){
        checkValueSpan(_this,"此项为必须项！");
        bool = false;
        return bool;
    }
    if(bool && arr != null){
        $(arr).each(function(){
            if(!this["reg"].test(val)){
                checkValueSpan(_this,this["msg"]);
                bool = false;
                return bool;
            }
        });
    }
    return bool;
}

function checkValueSpan(_this,msg){
    $(_this).parent().after("<span class='showMsgSpan' style='color:red'>"+msg+"</span>");
}

function add0(m){return m<10?'0'+m:m }

function format(timestamp) {
    //timestamp是整数，否则要parseInt转换,不会出现少个0的情况
    var time = new Date(timestamp);
    var year = time.getFullYear();
    var month = time.getMonth()+1;
    var date = time.getDate();
    var hours = time.getHours();
    var minutes = time.getMinutes();
    var seconds = time.getSeconds();
    return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes)+':'+add0(seconds);
}

function sendAjax(islayer,bool,ajaxData,successfunction){
    if(islayer){
        layer.open({type:2,shade:'background-color: rgba(0,0,0,.3)',shadeClose:false});
    }
    $.ajax({
        async: bool,
        type: "POST",
        url: "../../servlet/dispatcherServlet",
        data: ajaxData,
        timeout: 1000 * 60,
        error: function(){
            //关闭加载
            alertMsg("访问错误，请联系管理员");
        },
        success: successfunction
    });
}

function fireScrollEvent(){
	if(document.createEvent){
		var evObj = document.createEvent('HTMLEvents');
		evObj.initEvent('scroll', true, true);
		document.dispatchEvent(evObj);
	} else if( document.createEventObject ) {
		document.fireEvent('onscroll');
	}
}
