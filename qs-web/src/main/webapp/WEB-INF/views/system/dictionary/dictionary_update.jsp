<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <%@include file="/WEB-INF/views/common/common_meta.jsp" %>
    <title>添加字典</title>
</head>
<body>
<article class="page-container">
    <form class="form form-horizontal" id="form_dictionary_update">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">上级字典：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="hidden" id="id" name="id">
                <input type="hidden" id="pid" name="pid"/>
                <input type="text" class="input-text" value="" placeholder="上级字典名称(点击后进行选择)" readonly="readonly"
                       id="pidname" name="pidname">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>字典名称：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="text" class="input-text" value="" placeholder="字典名称" id="name" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>编码：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <input type="text" class="input-text" placeholder="编码" id="code" name="code">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>是否启用：</label>
            <div class="formControls col-xs-4 col-sm-5">
                <select id="isactive" name="isactive" class="select-box select">

                    <option value="1" selected>启用</option>
                    <option value="0">禁用</option>

                </select>
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
        src="<c:url value="/js/system/dictionary/dictionary_update.js" /> "></script>
<script type="text/javascript">
    var id = '${id}';
    DictionaryUpdate.init();
</script>
</body>
</html>