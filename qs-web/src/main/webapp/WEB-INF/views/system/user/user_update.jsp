<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>修改用户</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="form_user_update">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>用户名：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="text" class="input-text" value="" placeholder="用户名" id="username" name="username">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>姓名：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="text" class="input-text" value="" placeholder="姓名" id="name" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>所属部门：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="hidden" id="departmentId" name="departmentId"/>
                <input type="text" class="input-text" placeholder="部门名称(点击后进行选择)" readonly id="departmentName"
                       name="departmentName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>角色：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <select id="roleId" name="roleId" class="select-box select"></select>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">手机号：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="text" class="input-text" value="" placeholder="手机号" id="phone" name="phone">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">邮箱：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="text" class="input-text" placeholder="邮箱" name="email" id="email">
            </div>
        </div>
        <br/>
        <div class="row cl">
            <div class="col-xs-7 col-sm-7 col-xs-offset-5 col-sm-offset-5">
                <input type="hidden" name="id" id="id">
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
        src="<c:url value="/js/system/user/user_update.js" /> "></script>
<script type="text/javascript">
    var id = '${id}';
    UserUpdate.init();
</script>
</body>
</html>