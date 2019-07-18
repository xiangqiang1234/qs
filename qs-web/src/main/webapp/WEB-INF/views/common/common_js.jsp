<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="<c:url value="/assets/plugins/jquery/1.9.1/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/plugins/layer/2.4/layer.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/h-ui/js/H-ui.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/assets/h-ui.admin/js/H-ui.admin.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="/assets/plugins/jquery.contextmenu/jquery.contextmenu.r2.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="/assets/plugins/datatables/1.10.0/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="/assets/plugins/laypage/1.2/laypage.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="/assets/plugins/laydate/laydate.js"/>"></script>
<!-- form fill 组件 -->
<script type="text/javascript" src="/assets/plugins/jquery.formFill.js"></script>

<script type="text/javascript"
        src="<c:url value="/assets/plugins/jquery.validation/1.14.0/jquery.validate.js" /> "></script>
<script type="text/javascript"
        src="<c:url value="/assets/plugins/jquery.validation/1.14.0/validate-methods.js" /> "></script>
<script type="text/javascript"
        src="<c:url value="/assets/plugins/jquery.validation/1.14.0/messages_zh.js" /> "></script>

<script type="text/javascript" src="<c:url value="/js/common/dataTableCommon.js" /> "></script>
<!-- 检查权限JS -->
<script type="text/javascript" src="<c:url value="/js/common/checkPermission.js" /> "></script>
<script type="text/javascript">
    CheckPermission.init();
</script>

<!--日期插件-->
<script type="text/javascript">
    function layDate(id, type) {
        laydate.render({
            elem: '#' + id,
            type: type
        });
    }

    function layDateMax(id, type, maxDate) {
        laydate.render({
            elem: '#' + id,
            type: type,
            max: maxDate
        });
    }

    function layDateFormat(id, format, type) {
        laydate.render({
            elem: '#' + id,
            format: format,
            type: type
        });
    }

    function layDateMaxFormat(id, format, type, maxDate) {
        laydate.render({
            elem: '#' + id,
            format: format,
            type: type,
            max: maxDate
        });
    }
</script>
<!--判断是否非空-->
<script type="text/javascript">
    function isNullOrEmpty(variable) {
        var isNullOrEmpty = false;
        if (variable === null || variable === undefined || variable.length === 0) {
            isNullOrEmpty = true;
        }
        return isNullOrEmpty;
    }
    function dataTableColumnWidth(data, render) {
        return {
            data: ifDataNull(data),
            render: render,
            sDefaultContent: ""
        };
    }
</script>
<!--如果数据为空的处理(替换数据，默认为[空])-->
<script type="text/javascript">
    function ifDataNull(expr1, expr2) {
        var defaultData = '[空]';
        if (!isNullOrEmpty(expr2)) {
            defaultData = expr2;
        }
        if (isNullOrEmpty(expr1)) {
            expr1 = defaultData;
        }
        return expr1;
    }
</script>
<!--datable工具-->
<script type="text/javascript">
    var DataTableUtil = function () {
        function createDataTable(table, tableColumns, tableUrl) {
            return table.DataTable({
                columns: tableColumns,
                sAjaxSource: tableUrl,
                bServerSide: true,
                sServerMethod: "POST",
                pagingType: "full_numbers",
                searching: false,
                lengthChange: false,
                sZeroRecords: "没有检索到数据",
                iDisplayLength: 10,
                bLengthChange: false,
                bDestroy: true,
                bSort: false
            });
        }

        return {
            createDataTable: function (table, tableColumns, tableUrl) {
                //验证session是否失效
                if (CheckPermission.checkSession()) {
                    return createDataTable(table, tableColumns, tableUrl);
                }
            }
        };
    }();
</script>
<!--DataTable相关列方法开始-->
<script type="text/javascript">
    function dataTableColumn(data, render) {
        return {
            data: ifDataNull(data),
            render: render,
            sDefaultContent: ""
        };
    }

    function dataTableCheckBox() {
        return function (data) {
            return '<input type="checkbox"  name="" value="' + data + '" />';
        };
    }
</script>
<!--DataTable相关列方法结束-->

<!--validator封装开始-->
<script type="text/javascript">
    var ValidatorUtil = function () {
        function validate(rules, messages, formId, url) {
            return formId.validate({
                errorElement: 'span',
                errorClass: 'help-inline',
                focusInvalid: false,
                ignore: "",
                onkeyup: false,
                rules: rules,
                messages: messages,
                errorPlacement: function (error, element) {
                    layer.tips(error.get(0).textContent, '#' + element.get(0).id, {
                        tipsMore: true
                    });
                },
                submitHandler: function (form) {
                    var options = {
                        url: url,
                        type: 'post',
                        dataType: 'json',
                        success: function (response) {
                            var icon = 2;
                            if (response.success) {
                                icon = 1;
                            }
                            layer.alert(response.message, {icon: icon, title: '提示', closeBtn: 0}, function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.$('#refreshBtn').click();
                                parent.layer.close(index);
                            });
                        },
                        error: function (result) {
                            layer.alert("连接服务器失败！", {icon: 5, title: '提示', closeBtn: 0}, function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.$('#refreshBtn').click();
                                parent.layer.close(index);
                            });
                        }
                    };
                    formId.ajaxSubmit(options);
                    return false;
                }

            });
        }

        function validate2(rules, messages, formId, url, searchMethod) {
            return formId.validate({
                errorElement: 'span',
                errorClass: 'help-inline',
                focusInvalid: false,
                ignore: "",
                onkeyup: false,
                rules: rules,
                messages: messages,
                errorPlacement: function (error, element) {
                    layer.tips(error.get(0).textContent, '#' + element.get(0).id, {
                        tipsMore: true
                    });
                },
                submitHandler: function (form) {
                    var options = {
                        url: url,
                        type: 'post',
                        dataType: 'json',
                        success: function (response) {
                            var icon = 2;
                            if (response.success) {
                                icon = 1;
                                searchMethod();
                            }
                            layer.alert(response.message, {icon: icon, title: '提示', closeBtn: 0});
                        },
                        error: function (result) {
                            layer.alert("连接服务器失败！", {icon: 5, title: '提示', closeBtn: 0});
                        }
                    };
                    formId.ajaxSubmit(options);
                    return false;
                }

            });
        }

        function validate3(rules, messages, formId, url, buttonName) {
            return formId.validate({
                errorElement: 'span',
                errorClass: 'help-inline',
                focusInvalid: false,
                ignore: "",
                onkeyup: false,
                rules: rules,
                messages: messages,
                errorPlacement: function (error, element) {
                    layer.tips(error.get(0).textContent, '#' + element.get(0).id, {
                        tipsMore: true
                    });
                },
                submitHandler: function (form) {
                    var options = {
                        url: url,
                        type: 'post',
                        dataType: 'json',
                        success: function (response) {
                            var icon = 2;
                            if (response.success) {
                                icon = 1;
                            }
                            layer.alert(response.message, {icon: icon, title: '提示', closeBtn: 0}, function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.$('#' + buttonName).click();
                                parent.layer.close(index);
                            });
                        },
                        error: function (result) {
                            layer.alert("连接服务器失败！", {icon: 5, title: '提示', closeBtn: 0}, function () {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.$('#' + buttonName).click();
                                parent.layer.close(index);
                            });
                        }
                    };
                    formId.ajaxSubmit(options);
                    return false;
                }

            });
        }

        return {
            validate: function (rules, messages, formId, url) {
                return validate(rules, messages, formId, url);
            },
            validate2: function (rules, messages, formId, url, searchMethod) {
                return validate2(rules, messages, formId, url, searchMethod);
            },
            validate3: function (rules, messages, formId, url, buttonName) {
                return validate3(rules, messages, formId, url, buttonName);
            }
        };
    }();

</script>
<!--validator封装结束-->

<!--公共的数据处理方法-->
<script type="text/javascript">
    /**
     * 回车键查询
     * @param id
     * @param searchMethod
     */
    function keyDownMethod(id, searchMethod) {
        $("#" + id).keydown(function (e) {
            var key = window.event ? e.keyCode : e.which;
            if (key.toString() == "13") {
                searchMethod();
                return false;
            }
        });
    }

    /**
     * 公共的修改数据方法
     * @param url
     * @param data
     * @param successMethod
     * @param message
     */
    function utilUpdateMethod(url, data, successMethod, message) {
        //验证session是否失效
        if (CheckPermission.checkSession()) {
            if (message != null && message != undefined && message != "") {
                layer.confirm(message, {icon: 0, title: '提示', closeBtn: 0, btn: ['确认', '取消']}, function () {
                    $.ajax({
                        url: url,
                        data: data,
                        type: 'post',
                        dataType: 'json',
                        success: function (response) {
                            var icon = 2;
                            if (response.success) {
                                icon = 1;
                            }
                            layer.alert(response.message, {icon: icon, title: '提示', closeBtn: 0});
                            successMethod();
                        },
                        error: function (result) {
                            layer.alert("连接服务器失败！", {icon: 5, title: '提示', closeBtn: 0});
                        }
                    });
                })
            } else {
                $.ajax({
                    url: url,
                    data: data,
                    type: 'post',
                    dataType: 'json',
                    success: function (response) {
                        var icon = 2;
                        if (response.success) {
                            icon = 1;
                        }
                        layer.alert(response.message, {icon: icon, title: '提示', closeBtn: 0});
                        successMethod();
                    },
                    error: function (result) {
                        layer.alert("连接服务器失败！", {icon: 5, title: '提示', closeBtn: 0});
                    }
                });
            }
        }
    }

    /**
     * 公共的验证唯一性方法
     * @param url
     * @param data
     * @param value
     * @returns {boolean}
     */
    function utilCheckUnique(url, data, value) {
        //验证session是否失效
        if (CheckPermission.checkSession()) {
            var returnFlag = true;
            if (value != undefined && value != "") {
                $.ajax({
                    url: url,
                    data: data,
                    type: 'post',
                    async: false,
                    dataType: 'json',
                    success: function (data) {
                        returnFlag = data;
                    }
                });
            }
            return returnFlag;
        }
    }

    /**
     * 公共的获取下拉列表数据方法
     * @param url
     * @param data
     * @param formId
     */
    function utilGetOptionsId(url, data, formId) {
        //验证session是否失效
        if (CheckPermission.checkSession()) {
            $.ajax({
                url: url,
                data: data,
                type: 'post',
                async: false,
                dataType: 'json',
                success: function (data) {
                    var content = "";
                    for (var i = 0; i < data.length; i++) {
                        content += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
                    }
                    formId.append(content);
                }
            });
        }
    }

    /**
     * 公共的获取下拉列表数据方法
     * @param url
     * @param data
     * @param formId
     */
    function utilGetOptionsCode(url, data, formId) {
        //验证session是否失效
        if (CheckPermission.checkSession()) {
            $.ajax({
                url: url,
                data: data,
                type: 'post',
                async: false,
                dataType: 'json',
                success: function (data) {
                    var content = "";
                    for (var i = 0; i < data.length; i++) {
                        content += "<option value='" + data[i].code + "'>" + data[i].name + "</option>";
                    }
                    formId.append(content);
                }
            });
        }
    }

    /**
     * 公共的获取字典数据方法
     * @param code
     * @param formId
     */
    function utilGetDictionaryId(code, formId) {
        var url = SERVER_CONTEXT + "/control/system/dictionary/getDictionaryByCode";
        var data = {"code": code};
        utilGetOptionsId(url, data, formId);
    }

    /**
     * 公共的获取字典数据方法
     * @param code
     * @param formId
     */
    function utilGetDictionaryCode(code, formId) {
        var url = SERVER_CONTEXT + "/control/system/dictionary/getDictionaryByCode";
        var data = {"code": code};
        utilGetOptionsCode(url, data, formId);
    }

    /**
     * 公共的获取详细信息方法
     * @param url
     * @param data
     * @param formId
     */
    function utilGetModel(url, data, formId) {
        //验证session是否失效
        if (CheckPermission.checkSession()) {
            $.ajax({
                url: url,
                data: data,
                type: 'post',
                dataType: 'json',
                success: function (response) {
                    if (response.success) {
                        var modelData = response.data;
                        formId.fill(modelData);
                    } else {
                        layer.alert(response.message, {icon: 0, title: '提示', closeBtn: 0}, function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.$('#refreshBtn').click();
                            parent.layer.close(index);
                        });

                    }
                }
            });
        }
    }

    /**
     * 公共的获取详情修改方法
     * @param url
     * @param data
     * @param editMethod
     * @param formId
     */
    function utilEditModel(url, data, editMethod, formId) {
        //验证session是否失效
        if (CheckPermission.checkSession()) {
            $.ajax({
                url: url,
                data: data,
                type: 'post',
                dataType: 'json',
                success: function (response) {
                    if (response.success) {
                        var modelData = response.data;
                        formId.fill(modelData);
                        editMethod();
                    } else {
                        layer.alert(response.message, {icon: 0, title: '提示', closeBtn: 0}, function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.$('#refreshBtn').click();
                            parent.layer.close(index);
                        });
                    }
                }
            });
        }
    }
</script>

<!--Date 格式化-->
<script type="text/javascript">
    /*
    对Date的扩展，将 Date 转化为指定格式的String
    月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    例子：
    (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    */
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                  //月份
            "d+": this.getDate(),                       //日
            "h+": this.getHours(),                      //小时
            "m+": this.getMinutes(),                    //分
            "s+": this.getSeconds(),                    //秒
            "q+": Math.floor((this.getMonth() + 3) / 3),//季度
            "S": this.getMilliseconds()                 //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
</script>
