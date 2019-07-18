/**
 * @author Administrator on 2017/12/23.
 */
var Department = function () {
    var listTable = $("#departmentTable");
    var checkNode = undefined;
    return {
        init: function () {
            search();
            initBtnEvent();
            //加载左边树
            loadTree();
        },
        search: search,
        showUpdateDiv: showUpdateDiv,
        del: del,
        reset: reset,
        loadTree: loadTree,
        showDataPermissionDiv:showDataPermissionDiv
    };

    function reset() {
        $("#name").val("");
        checkNode = undefined;
        loadTree();
        search();
    }

    /**
     * 加载左边树
     */
    function loadTree() {
        var setting = {
            treeId: "departmentTree",
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
        var url = SERVER_CONTEXT + "/control/system/departmentManger/showLeftTree";
        var data = {};
        $.ajax({
            url: url,
            data: data,
            type: 'post',
            dataType: 'json',
            async: false,
            success: function (response) {
                $("#pid").val("");
                $("#pName").val("");
                $("#pSerialNumber").val("");
                var t = $("#treeDemo");
                for (var i = 0; i < response.length; i++) {
                    var node = response[i];
                    node.open = true;   //表示打开子节点
                    node.iconOpen = "/assets/img/tree/bu_men.png";
                    node.iconClose = "/assets/img/tree/bu_men_close.png";
                    node.icon = "/assets/img/tree/bu_men.png";
                    if(checkNode !== undefined && checkNode.id === node.id){
                        checkNode = node;
                        treeItemClick("",node);
                    }
                }
                var zTree = $.fn.zTree.init(t, setting, response);
                if(checkNode !== undefined){
                    zTree.selectNode(zTree.getNodeByParam("id",checkNode.id),true);
                }

            }
        });
    }

    /**
     * 左侧树点击节点查询
     */
    function treeItemClick(treeId, treeNode) {
        checkNode = treeNode;
        $("#pid").val(treeNode.id);
        $("#pName").val(treeNode.name);
        $("#pSerialNumber").val(treeNode.serialNumber);
        search();
    }


    /**
     *初始化btn事件
     */
    function initBtnEvent() {
        $("#refreshBtn").unbind("click").bind("click", function () {
            loadTree();
            search();
        });
        $("#addBtn").unbind("click").bind("click", function () {
            layer_show("新增单位", SERVER_CONTEXT + "/control/system/departmentManger/addUi?pid="+$("#pid").val()+"&pName="+$("#pName").val()+"&pSerialNumber="+$("#pSerialNumber").val(), "800", "400", null);
        });
        keyDownMethod("name", search);
    }

    /**
     * 查询
     */
    function search() {
        var name = $("#name").val();
        var pSerialNumber = $("#pSerialNumber").val();
        var queryString = "?name=" + name + "&pSerialNumber=" + pSerialNumber;
        loadDataTable(queryString);
    }

    /**
     * 查询 创建table
     */
    function loadDataTable(queryString) {
        var tableUrl = SERVER_CONTEXT + "/control/system/departmentManger/list" + queryString;
        var columns = [
            dataTableColumn("name"),
            dataTableColumn("no"),
            dataTableColumn("phone"),
            {
                data: "id",
                sDefaultContent: "",
                render: function (data, type, row, meta) {
                    var content = '';
                    if (CheckPermission.hasPermission("/button/system/departmentManger/update")) {
                        content += '<a title="修改" onclick="Department.showUpdateDiv(\'' + data + '\')"><i class="Hui-iconfont">&#xe6df;</i></a> ';
                    }
                    if (CheckPermission.hasPermission("/button/system/departmentManger/delete")) {
                        content += '&nbsp;<a title="删除" onclick="Department.del(\'' + data + '\')"><i class="Hui-iconfont">&#xe6e2;</i></a> ';
                    }
                    if (CheckPermission.hasPermission("/button/system/departmentManger/sjfq")) {
                        content += '&nbsp;<a title="数据赋权" onclick="Department.showDataPermissionDiv(\'' + data + '\')"><i class="Hui-iconfont">&#xe61f;</i></a>';
                    }
                    return content;
                }
            }
        ];
        DataTableUtil.createDataTable(listTable, columns, tableUrl);
    }

    /**
     * 删除
     * @param id
     */
    function del(id) {
        var url = SERVER_CONTEXT + "/control/system/departmentManger/delete";
        var data = {"id": id};
        utilUpdateMethod(url, data, updateRefresh, "确定要删除吗？");
    }

    /**
     * 修改数据后触发的方法
     */
    function updateRefresh() {
        search();
        loadTree();
    }

    /**
     * 显示修改页面
     * @param id
     */
    function showUpdateDiv(id) {
        layer_show("修改单位信息", SERVER_CONTEXT + "/control/system/departmentManger/updateUi?id=" + id, "800", "400", null);
    }

    /**
     * 显示数据赋权页面
     * @param id
     */
    function showDataPermissionDiv(id) {
        layer_show("选择数据权限", SERVER_CONTEXT + "/control/system/departmentManger/setDataPermissionUi?type=1&id=" + id, "500", "700", null);
    }

}();