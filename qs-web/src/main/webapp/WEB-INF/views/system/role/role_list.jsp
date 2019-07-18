<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>角色管理</title>
</head>
<body>

<!--面包屑开始==================================-->
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 系统管理
    <span class="c-gray en">&gt;</span>角色管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" id="refreshBtn"
       title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<!--面包屑结束==================================-->


<!--内容开始==================================-->
<div class="page-container">
    <shiro:hasPermission name="/button/system/role/add">
        <div class="cl pd-5 bg-1 bk-gray mt-20">
            <a id="addBtn" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
        </div>
    </shiro:hasPermission>
    <div class="mt-20">
        <table id="roleTable" class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th style="width: 25%">角色名称</th>
                <th style="width: 25%">唯一标识</th>
                <th style="width: 25%">描述</th>
                <th style="width: 25%">操作</th>
            </tr>
            </thead>
        </table>
    </div>

</div>
<!--内容结束==================================-->
<!--引入系统路径js-->
<%@include file="/WEB-INF/views/common/common_path.jsp" %>
<!--引入公共js-->
<%@include file="/WEB-INF/views/common/common_js.jsp" %>
<!--下面引入业务相关js-->
<script type="text/javascript"
        src="<c:url value="/js/system/role/role.js" /> "></script>
<script type="text/javascript">
    Role.init();
</script>
</body>
</html>