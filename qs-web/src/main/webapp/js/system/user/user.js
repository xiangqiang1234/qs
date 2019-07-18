/**
 * User.js
 *
 */

var User = function () {
    var $userTable = $("#userTable");

    /**
     *初始化btn事件
     */
    function initBtnEvent() {
        $("#refreshBtn").unbind("click").bind("click", function () {
            reset();
        });
        $("#addBtn").unbind("click").bind("click", function () {
            layer_show("添加用户", SERVER_CONTEXT + "/control/system/userManger/addUi", "800", "500", null);
        });
        keyDownMethod("param", search);
    }

    /**
     * 查询
     */
    function search() {
        var param = $("#param").val();
        var queryString = "";
        if (!isNullOrEmpty(param)) {
            queryString = "?param=" + encodeURI(encodeURI(param));
        }
        loadDataTable(queryString);
    }

    /**
     * 重置
     */
    function reset() {
        $("#param").val("");
        search();
    }

    /**
     * 创建table
     */
    function loadDataTable(queryString) {
        var tableUrl = SERVER_CONTEXT + "/control/system/userManger/list" + queryString;
        var columns = [
            dataTableColumn("username"),
            dataTableColumn("name"),
            dataTableColumn("departmentName"),
            dataTableColumn("roleName"),
            dataTableColumn("phone"),
            dataTableColumn("email"),
            {
                data: "state", render: function (data, type, row, meta) {
                    var content;
                    if (data == "0") {
                        content = '<span class="label label-danger radius">已冻结</span>';
                    } else if (data == "1") {
                        content = '<span class="label label-success radius">已启用</span>';
                    }
                    return content;
                }
            },
            {
                data: "id",
                render: function (data, type, row, meta) {
                    var content = '';
                    if (CheckPermission.hasPermission("/button/system/userManger/isActive")) {
                        if (row["state"] == "0") {
                            content += '&nbsp;<a title="解冻" onclick="User.userBlock(\'' + data + '\',\'1\')"><i class="Hui-iconfont">&#xe605;</i></a> ';
                        }else{
                            content += '<a title="冻结" onclick="User.userBlock(\'' + data + '\',\'0\')"><i class="Hui-iconfont">&#xe60e;</i></a> ';
                        }
                    }
                    if (CheckPermission.hasPermission("/button/system/userManger/update")) {
                        content += '&nbsp;<a title="修改" onclick="User.showUpdateDiv(\'' + data + '\')"><i class="Hui-iconfont">&#xe6df;</i></a> '
                    }
                    if (CheckPermission.hasPermission("/button/system/userManger/passwordReset")) {
                        content += '&nbsp;<a title="重置密码" onclick="User.passwordReset(\'' + data + '\')"><i class="Hui-iconfont">&#xe63f;</i></a> '
                    }
                    if (CheckPermission.hasPermission("/button/system/userManger/sjfq")) {
                        content += '&nbsp;<a title="数据赋权" onclick="User.showDataPermissionDiv(\'' + data + '\')"><i class="Hui-iconfont">&#xe61f;</i></a>';
                    }
                    return content;
                }
            }
        ];
        DataTableUtil.createDataTable($userTable, columns, tableUrl);
    }

    /**
     * 用户冻结/解冻
     * @param id
     * @param state
     */
    function userBlock(id, state) {
        var url = SERVER_CONTEXT + "/control/system/userManger/userBlock";
        var data = {"id": id, "state": state};
        utilUpdateMethod(url, data, search, "");
    }

    /**
     * 重置密码
     * @param id
     */
    function passwordReset(id) {
        var url = SERVER_CONTEXT + "/control/system/userManger/passwordReset";
        var data = {"id": id};
        utilUpdateMethod(url, data, search, "确定要重置密码吗？");
    }

    /**
     * 显示修改页面
     * @param id
     */
    function showUpdateDiv(id) {
        layer_show("修改用户", SERVER_CONTEXT + "/control/system/userManger/updateUi?id=" + id, "800", "500", null);
    }

    /**
     * 显示数据赋权页面
     * @param id
     */
    function showDataPermissionDiv(id) {
        layer_show("选择数据权限", SERVER_CONTEXT + "/control/system/departmentManger/setDataPermissionUi?type=2&id=" + id, "500", "700", null);
    }

    return {
        init: function () {
            search();
            initBtnEvent();
        },
        search: search,
        reset: reset,
        userBlock: userBlock,
        passwordReset: passwordReset,
        showUpdateDiv: showUpdateDiv,
        showDataPermissionDiv:showDataPermissionDiv
    };
}();