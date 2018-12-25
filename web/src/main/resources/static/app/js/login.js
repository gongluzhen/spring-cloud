 $(document).ready(function () {
    $("#loginForm").validate({
        rules: {
            validateCode: {remote: ctx + "servlet/validateCodeServlet"}
        },
        messages: {
            username: {required: "请填写用户名."}, password: {required: "请填写密码."},
            validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
        },
        errorLabelContainer: "#messageBox",
        errorPlacement: function (error, element) {
            error.appendTo($("#loginError").parent());
        }
    });
});
// 如果在框架或在对话框中，则弹出提示并跳转到首页
if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
    alert('未登录或登录超时。请重新登录，谢谢！');
    top.location = ctx;
}