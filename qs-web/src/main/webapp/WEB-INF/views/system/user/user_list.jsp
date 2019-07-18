<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>用户管理</title>
</head>
<body>

<!--面包屑开始==================================-->
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 系统管理
    <span class="c-gray en">&gt;</span>用户管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" id="refreshBtn"
       title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<!--面包屑结束==================================-->


<!--内容开始==================================-->
<div class="page-container">
    <shiro:hasPermission name="/button/system/userManger/get">
        <div class="text-l">
            <input type="text" class="input-text" style="width:200px" placeholder="输入用户名、姓名、电话" id="param">
            <button class="btn btn-success radius" onclick="User.search()"><i class="Hui-iconfont">&#xe665;</i>
                查询
            </button>
            <button class="btn btn-secondary active radius" onclick="User.reset()"><i class="Hui-iconfont">&#xe66b;</i>
                重置
            </button>
        </div>
    </shiro:hasPermission>
    <shiro:hasPermission name="/button/system/userManger/add">
        <div class="cl pd-5 bg-1 bk-gray mt-20">
            <a id="addBtn" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新增</a>
        </div>
    </shiro:hasPermission>
    <div class="mt-20">
        <table id="userTable" class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th style="width: 10%">用户名</th>
                <th style="width: 10%">姓名</th>
                <th style="width: 15%">所属部门</th>
                <th style="width: 15%">角色</th>
                <th style="width: 10%">手机号</th>
                <th style="width: 15%">邮箱</th>
                <th style="width: 10%">状态</th>
                <th style="width: 15%">操作</th>
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
        src="<c:url value="/js/system/user/user.js" /> "></script>
<script type="text/javascript">
    User.init();
</script>
</body>
</html>