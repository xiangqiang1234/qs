<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>添加部门</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="form_department_add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">上级部门：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="hidden" id="pid" name="pid"/>
                <input type="hidden" id="pSerialNumber" name="pSerialNumber"/>
                <input type="text" class="input-text" value="" placeholder="上级部门名称(点击后进行选择)" readonly id="pidName"
                       name="pidName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>部门名称：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="text" class="input-text" value="" placeholder="部门名称" id="name" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>部门编号：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="text" class="input-text" placeholder="部门编号" id="no" name="no">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>联系电话：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="text" class="input-text" placeholder="联系电话" id="phone" name="phone">
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
        src="<c:url value="/js/system/department/department_add.js" /> "></script>
<script type="text/javascript">
    var pid = '${pid}';
    var pName = '${pName}';
    var pSerialNumber = '${pSerialNumber}';
    DepartmentAdd.init();
</script>
</body>
</html>