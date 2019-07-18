<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>修改密码</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="form_user_updatePassword">

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4" style="text-align:right"><span class="c-red">*</span>&nbsp;原密码：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="password" class="input-text" placeholder="原密码" id="oldPassword" name="oldPassword">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4" style="text-align:right"><span class="c-red">*</span>&nbsp;新密码：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="password" class="input-text" placeholder="新密码" id="newPassword" name="newPassword">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4" style="text-align:right"><span class="c-red">*</span>&nbsp;再次输入密码：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="password" class="input-text" placeholder="再次输入密码" id="newPassword2" name="newPassword2">
            </div>
        </div>
        <br/><br/>
        <div class="row cl">
            <div class="col-xs-5 col-sm-5 col-xs-offset-5 col-sm-offset-4">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</article>

<!--引入系统路径js-->
<%@include file="/WEB-INF/views/common/common_path.jsp" %>
<!--引入公共js-->
<%@include file="/WEB-INF/views/common/common_js.jsp" %>
<!--/请在上方写此页面业务相关的脚本-->
<script type="text/javascript"
        src="<c:url value="/js/system/user/user_updatePassword.js" /> "></script>
<script type="text/javascript">
    UserUpdatePassword.init();
</script>
</body>
</html>