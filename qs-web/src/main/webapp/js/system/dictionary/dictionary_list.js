/**
 * @author Administrator on 2017/12/23.
 */
var Dictionary = function () {
    var listTable = $("#dictionaryTable");

    function reset(){
        $("#dictionaryName").val("");
        search();
    }

    /**
     * 加载左边树
     */
    function loadTree(){
        var setting={
            treeId:"dictionaryTree",
            view: {
                dblClickExpand: false,
                showLine: false,
                selectedMulti: true,
                showIcon: true
            },
            data: {
                simpleData: {
                    enable:true,
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
        var url = SERVER_CONTEXT+"/control/system/dictionary/showLeftTree";
        var data = {};
        $.ajax({
            url:url,
            data:data,
            type:'post',
            dataType:'json',
            async: false,
            success:function(response){
                var t = $("#treeDemo");
                for (var i = 0; i < response.length; i++) {
                    var node = response[i];
                    node.open = true;   //表示打开子节点
                }
                $.fn.zTree.init(t, setting, response);
            }
        });
    }

    /**
     * 左侧树点击节点查询
     */
    function treeItemClick(treeId, treeNode){
        var queryString = "?pid="+treeNode.id;
        loadDataTable(queryString);
    }


    /**
     *初始化btn事件
     */
    function initBtnEvent() {

        $("#refreshBtn").unbind("click").bind("click", function () {
            reset();
            loadTree();
        });
        $("#addBtn").unbind("click").bind("click", function () {
            layer_show("添加字典", SERVER_CONTEXT + "/control/system/dictionary/addUi", "800", "500", null);
        });
        keyDownMethod("dictionaryName", search);
    }

    /**
     * 查询
     */
    function search() {
        var param = $("#dictionaryName").val();
        var queryString = "";
        if (!isNullOrEmpty(param)) {
            queryString = "?dictionaryName=" + encodeURI(encodeURI(param));
        }
        loadDataTable(queryString);
    }

    /**
     * 查询 创建table
     */
    function loadDataTable(queryString) {
        var tableUrl = SERVER_CONTEXT + "/control/system/dictionary/list" + queryString;
        var columns = [
            dataTableColumn("name"),
            dataTableColumn("code"),
            {
                data: "isactive",
                sDefaultContent: "",
                render: function (data, type, row, meta) {
                    var content;
                    if (data == "0") {
                        content = '<span class="label label-danger radius">已禁用</span>';
                    } else if (data == "1") {
                        content = '<span class="label label-success radius">已启用</span>';
                    }
                    return content;
                }
            },
            {
                data: "id",
                sDefaultContent: "",
                render: function (data, type, row, meta) {
                    var content = '';
                    var isActive = row["isactive"];
                    if (CheckPermission.hasPermission("/button/system/dictionary/isActive")) {
                        if (isActive == "1") {
                            content += '<a title="禁用" onclick="Dictionary.updateStatus(\'' + data + '\',\'0\')"><i class="Hui-iconfont">&#xe60e;</i></a> ';
                        } else {
                            content += '<a title="启用" onclick="Dictionary.updateStatus(\'' + data + '\',\'1\')"><i class="Hui-iconfont">&#xe605;</i></a> ';
                        }
                    }
                    if (CheckPermission.hasPermission("/button/system/dictionary/update")) {
                        content += '&nbsp;<a title="修改" onclick="Dictionary.showUpdateDiv(\'' + data + '\')"><i class="Hui-iconfont Hui-iconfont-edit"></i></a> ';
                    }
                    return content;
                }
            }
        ];
        DataTableUtil.createDataTable(listTable, columns, tableUrl);
    }

    /**
     * 启用禁用
     * @param id
     * @param isActive
     */
    function updateStatus(id, isActive) {
        var url = SERVER_CONTEXT + "/control/system/dictionary/updateStatus";
        var data = {"id": id, "isactive": isActive};
        utilUpdateMethod(url, data, search, "");
    }

    /**
     * 修改
     * @param id
     */
    function showUpdateDiv(id) {
        layer_show("修改字典", SERVER_CONTEXT + "/control/system/dictionary/updateUi?id=" + id, "800", "500", null);
    }

    return {
        init: function () {
            search();
            initBtnEvent();
            //加载左边树
            loadTree();
        },
        search: search,
        showUpdateDiv: showUpdateDiv,
        updateStatus: updateStatus,
        reset:reset,
        loadTree:loadTree
    };
}();