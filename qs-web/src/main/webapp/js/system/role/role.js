/**
 * User.js
 *
 */

var Role = function () {
    var roleTable = $("#roleTable");

    /**
     * 初始化时间
     */
    function initDate() {
        layDate("startDate", "datetime");
        layDate("endDate", "datetime");
    }

    /**
     *初始化btn事件
     */
    function initBtnEvent() {
        $("#refreshBtn").unbind("click").bind("click", function () {
            search();
        });
        $("#addBtn").unbind("click").bind("click", function () {
            layer_show("添加角色", SERVER_CONTEXT + "/control/system/role/addUi", "800", "400", null);
        });
    }

    /**
     * 查询 创建table
     */
    function search() {
        var tableUrl = SERVER_CONTEXT + "/control/system/role/list";
        var columns = [
            dataTableColumn("roleName"),
            dataTableColumn("roleSign"),
            dataTableColumn("description"),
            {
                data: "id",
                render: function (data, type, row, meta) {
                    var roleSign = row['roleSign'];
                    var content = '';
                    if(roleSign !== "admin"){
                        if (CheckPermission.hasPermission("/button/system/role/update")) {
                            content += '<a title="修改" onclick="Role.showUpdateDiv(\'' + data + '\')"><i class="Hui-iconfont">&#xe6df;</i></a>';
                        }
                        if (CheckPermission.hasPermission("/button/system/role/setPermission")) {
                            content += '&nbsp;<a title="赋权" onclick="Role.showSetPermissionDiv(\'' + data + '\')"><i class="Hui-iconfont">&#xe61f;</i></a>';
                        }
                    }
                    return content;
                }
            }
        ];
        DataTableUtil.createDataTable(roleTable, columns, tableUrl);
    }

    /**
     * 显示修改页面
     */
    function showUpdateDiv(id) {
        layer_show("修改角色", SERVER_CONTEXT + "/control/system/role/updateUi?id=" + id, "800", "400", null);
    }

    /**
     * 显示赋权页面
     */
    function showSetPermissionDiv(id) {
        layer_show("修改权限", SERVER_CONTEXT + "/control/system/role/setPermissionUi?id=" + id, "600", "600", null);
    }

    return {
        init: function () {
            initDate();
            search();
            initBtnEvent();
        },
        search: search,
        showUpdateDiv: showUpdateDiv,
        showSetPermissionDiv: showSetPermissionDiv
    };
}();