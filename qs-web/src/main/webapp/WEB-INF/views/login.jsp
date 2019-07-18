<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <%--<link rel="Bookmark" href="/assets/img/favicon.ico">--%>
    <%--<link rel="Shortcut Icon" href="/assets/img/favicon.ico"/>--%>
    <!--引入登陆样式-->
    <link href="<c:url value="/assets/h-ui/css/H-ui.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/assets/h-ui.admin/css/H-ui.login.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/assets/plugins/Hui-iconfont/1.0.8/iconfont.css"/>" rel="stylesheet" type="text/css"/>

    <!-- 引入浏览器兼容文件-->
    <%@include file="/WEB-INF/views/common/browser_compatibility.jsp" %>


    <title>车辆智能管理平台</title>
    <%--<meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">--%>
    <%--<meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">--%>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value=""/>
<div class="loginWraper">
    <div style="position:absolute;width:617px; height:10px; left:50%; top:50%; margin-left:-180px; margin-top:-260px;font-weight: bold;letter-spacing:3px;font-size: 45px;color:#ffffff">
        车辆智能管理平台
    </div>
    <div class="loginBox">
        <form class="form form-horizontal" id="loginForm" action="/control/user/login"
              method="post">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont" style="color: white">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input id="username" name="username" type="text" placeholder="账户" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont" style="color: white">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-4">
                    <input type="submit" class="btn btn-success radius size-L"
                           value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
                    <input style="margin-left:10px" type="reset" class="btn btn-default radius size-L"
                           value="&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置&nbsp;">
                </div>
            </div>
        </form>
    </div>
</div>
<%--<div class="footer">Copyright 潍坊力创电子科技有限公司 2018 © 环保监测平台 V1.0</div>--%>
<script type="text/javascript" src="<c:url value="/assets/plugins/jquery/1.9.1/jquery.min.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="/assets/plugins/jquery.validation/1.14.0/jquery.validate.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/plugins/layer/2.4/layer.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/system/login/login.js"/>"></script>
<script>
    Login.init();
</script>
<!--判断是否非空-->
<script type="text/javascript">
    function isNullOrEmpty(variable) {
        var isNullOrEmpty = false;
        if (variable === null || variable === undefined || variable.length === 0) {
            isNullOrEmpty = true;
        }
        return isNullOrEmpty;
    }
</script>
<script>
    //返回登录错误信息
    var error = '${error}';
    if (!isNullOrEmpty(error)) {
        layer.alert(error, {icon: 0, title: '提示', time: 5000});
    }

</script>
<script>
    window.onload = function () {
        if (window.parent.window != window) {
            layer.alert("登录超时，请重新登录！", {icon: 0, title: '提示', closeBtn: 0}, function () {
                window.top.location = "/control/page/login";
            });
        }
    }
</script>
</body>
</html>