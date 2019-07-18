<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>添加权限</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="form_permission_add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">上级权限：</label>
            <div class="formControls col-xs-4 col-sm-6">
                <input type="hidden" id="pid" name="pid"/>
                <input type="text" class="input-text" value="" onclick="PermissionAdd.addFormShowPerTreeUi();"
                       placeholder="上级权限名称(点击后进行选择)" readonly="readonly" id="pidname" name="pidname">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>权限名称：</label>
            <div class="formControls col-xs-4 col-sm-6">
                <input type="text" class="input-text" value="" placeholder="权限名称" id="permissionName"
                       name="permissionName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>链接地址：</label>
            <div class="formControls col-xs-4 col-sm-6">
                <input type="text" class="input-text" placeholder="链接地址" id="permissionSign" name="permissionSign">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>权限类型：</label>
            <div class="formControls col-xs-4 col-sm-6">
                <select id="level" name="level" class="select-box select">
                    <option value="" selected>--请选择--</option>
                    <option value="0">菜单</option>
                    <option value="1">功能</option>
                    <option value="2">子权限</option>
                </select>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>排序：</label>
            <div class="formControls col-xs-4 col-sm-6">
                <input type="text" class="input-text" placeholder="排序" id="sortNo" name="sortNo">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">权限描述：</label>
            <div class="formControls col-xs-4 col-sm-6">
                <textarea class="textarea" placeholder="权限描述" id="description" name="description" rows="3"></textarea>
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
        src="<c:url value="/js/system/permission/permission_add.js" /> "></script>

<script type="text/javascript">
    PermissionAdd.init();
</script>
</body>
</html>