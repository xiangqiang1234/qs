/**
 * 设置部门权限 JS
 * @author yuzj  2019-03-05
 * @type {{init}}
 */
var SetDataPermission = function () {
    /**
     *初始化btn事件
     */
    function initBtnEvent() {
        $("#addBtn").unbind("click").bind("click", function () {
            setPermission();
        });
    }

    /**
     * 获取权限树数据
     */
    function getPermissionTree() {
        var url = SERVER_CONTEXT + "/control/system/departmentManger/showLeftTree";
        var data = {"userDeptId": id,"type":type};
        $.ajax({
            url: url,
            data: data,
            type: 'post',
            dataType: 'json',
            success: function (response) {
                createTree(response);
            }
        });
    }

    /**
     * 创建权限树
     * @param listData
     */
    function createTree(listData) {
        var setting = {
            treeId: "treeDemo",
            view: {
                dblClickExpand: true,
                showLine: true,
                selectedMulti: true,
                showIcon: true,
                fontCss: setFontCss
            },
            check: {
                enable: true,  //显示 复选框或单选框
                autoCheckTrigger: true,   //true / false 分别表示 触发 / 不触发 事件回调函数
                chkStyle: "checkbox",  //勾选框类型(checkbox 或 radio）
                chkboxType :{ "Y" : "s", "N" : "s" } //Y:勾选（参数：p:影响父节点），N：不勾（参数s：影响子节点）[p 和 s 为参数]
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId"
                }
            }
        };
        for (var i = 0; i < listData.length; i++) {
            var node = listData[i];
            node.open = true;   //表示打开子节点
            if(node.nocheck){
                node.name = node.name + "（无权限）"
            }
        }
        $.fn.zTree.init($("#treeDemo"), setting, listData);
    }

    /**
     * 设置节点样式
     *
     * @param treeId
     * @param treeNode
     * @returns {*}
     */
    function setFontCss(treeId, treeNode) {
        if(treeNode.nocheck){
            return {color:"red"};
        }else {
            return {};
        }
    }

    /**
     * 保存
     */
    function setPermission() {
        var ids = [];
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        for(var i=0;i<nodes.length;i++){
            ids.push(nodes[i].id);
        }
        var url = SERVER_CONTEXT + "/control/system/departmentManger/setDataPermission";
        var data = {"ids": ids,"id":id,"type":type};
        if(ids.length === 0){
            layer.alert("权限不能为空！", {icon: 0, title: '提示', closeBtn: 0});
        }else{
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
                    layer.alert(response.message, {icon: icon,title:'提示',closeBtn:0},function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    });
                },
                error: function (result) {
                    layer.alert("连接服务器失败！", {icon: 5, title: '提示', closeBtn: 0}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    });
                }
            });
        }
    }

    return {
        init: function () {
            initBtnEvent();
            getPermissionTree();
        }
    };
}();