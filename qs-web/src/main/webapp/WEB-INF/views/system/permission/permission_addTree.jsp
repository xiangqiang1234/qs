<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <!--zTree-->
    <link href="<c:url value="/assets/plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css"/>" rel="stylesheet"
          type="text/css"/>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>

    <title>上级权限</title>
</head>
<body>
<article class="page-container">

    <div class="row cl">

        <div class="formControls col-xs-4 col-sm-6">
            <ul id="treeList" class="ztree"></ul>
        </div>
    </div>

</article>

<!--引入系统路径js-->
<%@include file="/WEB-INF/views/common/common_path.jsp" %>
<!--引入公共js-->
<%@include file="/WEB-INF/views/common/common_js.jsp" %>
<!--/请在上方写此页面业务相关的脚本-->
<script type="text/javascript"
        src="<c:url value="/js/system/permission/permission_addTree.js" /> "></script>
<!-- zTre插件 -->
<script type="text/javascript"
        src="<c:url value="/assets/plugins/zTree/v3/js/jquery.ztree.all-3.5.min.js" /> "></script>


<script type="text/javascript">
    PermissionAddTree.init();
</script>
</body>
</html>