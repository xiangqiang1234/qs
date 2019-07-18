var DepartmentAddTree = function () {

    return {
        init: function () {
            getDepartmentTree();
        },
        treeItemClick: treeItemClick
    };

    /**
     * ztree 参数初始化
     */
    function getDepartmentTree() {
        var setting = {
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
                var tree = $("#treeList");
                for (var i = 0; i < response.length; i++) {
                    var node = response[i];
                    node.open = true;   //表示打开子节点
                    node.iconOpen = "/assets/img/tree/bu_men.png";
                    node.iconClose = "/assets/img/tree/bu_men_close.png";
                    node.icon = "/assets/img/tree/bu_men.png";
                }
                $.fn.zTree.init(tree, setting, response);
            }
        });
    }

    /**
     * 添加上级权限
     */
    function treeItemClick(treeId, treeNode) {
        if (pageId === "user") {
            $("#departmentId", parent.document).val(treeNode.id);
            $("#departmentName", parent.document).val(treeNode.name);
        } else if (pageId === "team") {
            $("#departmentId", parent.document).val(treeNode.id);
            $("#departmentName", parent.document).val(treeNode.name);
            $("#userIds", parent.document).val("");
            $("#userNames", parent.document).val("");
        } else if (pageId === "base") {
            $("#departmentId", parent.document).val(treeNode.id);
            $("#deptName", parent.document).val(treeNode.name);
        } else {
            $("#pid", parent.document).val(treeNode.id);
            $("#pidName", parent.document).val(treeNode.name);
            $("#pSerialNumber", parent.document).val(treeNode.serialNumber);
        }

        layer_close();
    }

}();