<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <!--zTree-->
    <link href="<c:url value="/assets/plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css"/>" rel="stylesheet"
          type="text/css"/>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>角色管理</title>
</head>
<body class="pos-r">
<div>
    <!--面包屑开始==================================-->
    <nav class="breadcrumb">
        <i class="Hui-iconfont">&#xe67f;</i> 首页
        <span class="c-gray en">&gt;</span> 系统管理
        <span class="c-gray en">&gt;</span>权限管理
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" id="refreshBtn"
           title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>
    <!--面包屑结束==================================-->

    <div class="pos-a" style="width:250px !important;   ">
        <ul id="permissionTree" class="ztree"></ul>
    </div>

    <!--内容开始==================================-->
    <div class="page-container" style="margin-left:250px;">
        <shiro:hasPermission name="/button/system/permissionManger/get">
            <div class="text-l">
                权限名称：
                <input type="text" class="input-text" style="width:200px" placeholder="输入权限名称" id="permissionName">
                类型：<select id="level" name="level" class="select-box select" style="width:100px">
                <option value="" selected>全部</option>
                <option value="0">菜单</option>
                <option value="1">功能</option>
                <option value="2">子权限</option>
            </select>
                <button class="btn btn-success radius" id="search" onclick="Permission.search();"><i
                        class="Hui-iconfont">&#xe665;</i>
                    查询
                </button>
                <button class="btn btn-secondary active radius" onclick="Permission.reset()"><i class="Hui-iconfont">&#xe66b;</i>
                    重置
                </button>
            </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="/button/system/permissionManger/add">
            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <a id="addBtn" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
            </div>
        </shiro:hasPermission>
        <div class="mt-20">
            <table id="permissionTable" class="table table-border table-bordered table-hover table-bg table-sort">
                <thead>
                <tr class="text-c">
                    <th style="width: 25%">权限名称</th>
                    <th style="width: 15%">上级权限</th>
                    <th style="width: 35%">链接地址</th>
                    <th style="width: 9%">权限类型</th>
                    <th style="width: 8%">排序</th>
                    <th style="width: 8%">操作</th>
                </tr>
                </thead>
            </table>
        </div>

    </div>
</div>
<!--内容结束==================================-->
<!--引入系统路径js-->
<%@include file="/WEB-INF/views/common/common_path.jsp" %>
<!--引入公共js-->
<%@include file="/WEB-INF/views/common/common_js.jsp" %>
<script type="text/javascript"
        src="<c:url value="/assets/plugins/zTree/v3/js/jquery.ztree.all-3.5.min.js" /> "></script>
<!--下面引入业务相关js-->
<script type="text/javascript"
        src="<c:url value="/js/system/permission/permission.js" /> "></script>
<script type="text/javascript">
    Permission.init();
</script>
</body>
</html>