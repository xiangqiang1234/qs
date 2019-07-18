/**
 * @author Administrator on 2017/12/23.
 */
var Permission = function () {
    var permissionTable = $("#permissionTable");
    var pid = "";
    return {
        init: function () {
            search();
            initBtnEvent();
            loadTree();
        },
        search: search,
        update: update,
        deletePer: deletePer,
        reset: reset,
        loadTree: loadTree
    };

    function reset() {
        $("#permissionName").val("");
        $("#level").val("");
        search();
    }

    /**
     *初始化btn事件
     */
    function initBtnEvent() {
        $("#refreshBtn").unbind("click").bind("click", function () {
            pid = "";
            loadTree();
            reset();
        });
        $("#addBtn").unbind("click").bind("click", function () {
            layer_show("添加权限", SERVER_CONTEXT + "/control/system/permissionManger/addUi", "800", "500", null);
        });
        keyDownMethod("permissionName", search);
    }

    /**
     * 加载左边树
     */
    function loadTree() {
        var setting = {
            treeId: "permissionTree",
            view: {
                dblClickExpand: false,
                showLine: false,
                selectedMulti: true,
                showIcon: true
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {
                onClick: function (e, treeId, treeNode) {
                    treeItemClick(treeId, treeNode);
                }
            }
        };
        var url = SERVER_CONTEXT + "/control/system/permissionManger/showTree";
        var data = {};
        $.ajax({
            url: url,
            data: data,
            type: 'post',
            dataType: 'json',
            async: false,
            success: function (response) {
                var t = $("#permissionTree");
                // for (var i = 0; i < response.length; i++) {
                    // var node = response[i];
                    // if(node.level === "0"){
                    //     node.open = true;   //表示打开子节点
                    // }else {
                    //     node.open = false;   //表示关闭子节点
                    // }
                // }
                $.fn.zTree.init(t, setting, response);
            }
        });
    }

    /**
     * 左侧树点击事件
     */
    function treeItemClick(treeId, treeNode){
        pid = treeNode.id;
        search();
    }

    /**
     * 查询
     */
    function search() {
        var permissionName = $("#permissionName").val();
        var level = $("#level").val();
        var queryString = "";
        if (!isNullOrEmpty(permissionName)) {
            queryString += "?permissionName=" + permissionName;
        }
        if (isNullOrEmpty(queryString)) {
            queryString += "?level=" + level;
        }else{
            queryString += "&level=" + level;
        }
        if (isNullOrEmpty(queryString)) {
            queryString += "?pid=" + pid;
        }else{
            queryString += "&pid=" + pid;
        }
        loadDataTable(queryString);
    }

    /**
     * 查询 创建table
     */
    function loadDataTable(queryString) {
        var tableUrl = SERVER_CONTEXT + "/control/system/permissionManger/list" + queryString;
        var columns = [
            dataTableColumn("permissionName"),
            dataTableColumn("pidname"),
            dataTableColumn("permissionSign"),
            {
                data: "level",
                render: function (data, type, row, meta) {
                    var content = '';
                    if(data == "0"){
                        content = "<span style='color: green'>菜单</span>";
                    }else if(data == "1"){
                        content = "<span style='color: blue'>功能</span>";
                    }else if(data == "2"){
                        content = "子权限";
                    }
                    return content;
                }
            },
            dataTableColumn("sortNo"),
            {
                data: "id",
                render: function (data, type, row, meta) {
                    var content = '';
                    if(CheckPermission.hasPermission("/button/system/permissionManger/update")){
                        content += '<a title="修改" onclick="Permission.update(\'' + data + '\')"><i class="Hui-iconfont">&#xe6df;</i></a> ';
                    }
                    if(CheckPermission.hasPermission("/button/system/permissionManger/delete")){
                        content += '<a title="删除" onclick="Permission.deletePer(\'' + data + '\')"><i class="Hui-iconfont">&#xe6e2;</i></a> ';
                    }
                    return content;
                }
            }
        ];
        DataTableUtil.createDataTable(permissionTable, columns, tableUrl);
    }

    /**
     *  删除
     */
    function deletePer(id) {
        layer.confirm("确定删除选择的数据？", {icon: 0, title: '提示', closeBtn: 0, btn: ['确认', '取消']}, function (result) {
            if (result) {
                var url = SERVER_CONTEXT + "/control/system/permissionManger/delete";
                var data = {"id": id};
                $.ajax({
                    url: url,
                    data: data,
                    type: 'post',
                    dataType: 'json',
                    success: function (response) {
                        if (response.success) {
                            layer.alert(response.message, {icon: 1, title: '提示', closeBtn: 0});
                        } else {
                            layer.alert(response.message, {icon: 0, title: '提示', closeBtn: 0});
                        }
                        search();
                        loadTree();
                    }
                });
            }
        })
    }

    /**
     * 修改
     * @param id
     */
    function update(id) {
        layer_show("修改权限", SERVER_CONTEXT + "/control/system/permissionManger/updateUi?id=" + id, "800", "500", null);
    }
}();