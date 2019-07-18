<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<link href="<c:url value="/assets/plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css"/>" rel="stylesheet" type="text/css"/>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>数据赋权</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="form_data_permission">
        <div class="row cl">
            <div class="formControls col-xs-4 col-sm-4">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-8 col-xs-offset-4 col-sm-offset-4">
                <a id="addBtn" class="btn btn-primary radius">&nbsp;&nbsp;提交&nbsp;&nbsp;</a>
            </div>
        </div>
    </form>
</article>

<!--引入系统路径js-->
<%@include file="/WEB-INF/views/common/common_path.jsp" %>
<!--引入公共js-->
<%@include file="/WEB-INF/views/common/common_js.jsp" %>
<!-- zTre插件 -->
<script type="text/javascript"
        src="<c:url value="/assets/plugins/zTree/v3/js/jquery.ztree.all-3.5.min.js" /> "></script>

<!--/请在上方写此页面业务相关的脚本-->
<script type="text/javascript"
        src="<c:url value="/js/system/department/department_setDataPermission.js" /> "></script>
<script type="text/javascript">
    var id = '${id}';
    var type = '${type}';
    SetDataPermission.init();
</script>
</body>
</html>