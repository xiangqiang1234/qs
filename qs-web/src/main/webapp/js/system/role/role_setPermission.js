/**
 * 角色赋权 JS
 * @author yuzj  2017-12-25
 * @type {{init}}
 */
var RoleSetPermission = function () {
    var form_setPermission = $("#form_role_permission");

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
     * @param id
     */
    function getPermissionTree(id) {
        form_setPermission.find("#id").val(id);
        var url = SERVER_CONTEXT + "/control/system/role/getPermissionTree";
        var data = {"id": id};
        $.ajax({
            url: url,
            data: data,
            type: 'post',
            dataType: 'json',
            success: function (response) {
                var listData = response.data;
                createTree(listData);
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
                showIcon: true
            },
            check: {
                enable: true,  //显示 复选框或单选框
                autoCheckTrigger: true,   //true / false 分别表示 触发 / 不触发 事件回调函数
                chkStyle: "checkbox"  //勾选框类型(checkbox 或 radio）
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
        }
        $.fn.zTree.init($("#treeDemo"), setting, listData);
    }

    /**
     * 保存
     */
    function setPermission() {
        var id=form_setPermission.find("#id").val();
        var ids = [];
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        for(var i=0;i<nodes.length;i++){
            ids.push(nodes[i].id);
        }
        var url = SERVER_CONTEXT + "/control/system/role/setPermission";
        var data = {"ids": ids,"id":id};
        if(ids.length==0){
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
            getPermissionTree(id);
        }
    };
}();