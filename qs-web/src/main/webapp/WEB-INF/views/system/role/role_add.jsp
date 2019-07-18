<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>添加角色</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="form_role_add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>角色名称：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="text" class="input-text" value="" placeholder="角色名称" id="roleName" name="roleName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>唯一标识：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <input type="text" class="input-text" value="" placeholder="唯一标识" id="roleSign" name="roleSign">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">描述：</label>
            <div class="formControls col-xs-4 col-sm-4">
                <textarea class="textarea" placeholder="描述" id="description" name="description" rows="3"></textarea>
            </div>
        </div>
        <br/>
        <div class="row cl">
            <div class="col-xs-7 col-sm-7 col-xs-offset-5 col-sm-offset-5">
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
        src="<c:url value="/js/system/role/role_add.js" /> "></script>
<script type="text/javascript">
    RoleAdd.init();
</script>
</body>
</html>