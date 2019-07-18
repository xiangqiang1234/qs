<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>修改部门</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="form_department_update">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">上级部门：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="hidden" id="pid" name="pid"/>
                <input type="hidden" id="pSerialNumber" name="pSerialNumber" value=""/>
                <input type="hidden" id="serialNumber" name="serialNumber" value=""/>
                <input type="text" class="input-text" value="" placeholder="上级部门名称(点击后进行选择)" readonly id="pidName"
                       name="pidName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>单位名称：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="text" class="input-text" value="" placeholder="单位名称" id="name" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>单位编号：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="text" class="input-text" placeholder="单位编号" id="no" name="no">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>单位电话：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="text" class="input-text" placeholder="单位电话" id="phone" name="phone">
            </div>
        </div>
        <br/>
        <div class="row cl">
            <div class="col-xs-7 col-sm-7 col-xs-offset-5 col-sm-offset-5">
                <input type="hidden" id="id" name="id"/>
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</article>

<!--_footer 作为公共模版分离出去-->
<!--引入系统路径js-->
<%@include file="/WEB-INF/views/common/common_path.jsp" %>
<!--引入公共js-->
<%@include file="/WEB-INF/views/common/common_js.jsp" %>
<!--/请在上方写此页面业务相关的脚本-->
<script type="text/javascript"
        src="<c:url value="/js/system/department/department_update.js" /> "></script>
<script type="text/javascript">
    var id = '${id}';
    DepartmentUpdate.init();
</script>
</body>
</html>