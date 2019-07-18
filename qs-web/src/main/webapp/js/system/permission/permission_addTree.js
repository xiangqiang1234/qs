var setting;
var zTreeObj;
var PermissionAddTree = function () {

    return {
        init: function () {
            initBtnEvent();
            treeInit();
        },
        treeItemClick:treeItemClick
    };

    /**
     * ztree 参数初始化
     */
    function initBtnEvent(){
        setting={
            treeId:"permissionTree",
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

    }

    /**
     * ztree 数值赋权
     */
    function treeInit(){
        var url = SERVER_CONTEXT+"/control/system/permissionManger/showTree";
        var data = {"level":1};
        $.ajax({
            url:url,
            data:data,
            type:'post',
            dataType:'json',
            async: false,
            success:function(response){
                for (var i = 0; i < response.length; i++) {
                    var node = response[i];
                    node.open = true;   //表示打开子节点
                }
                var t = $("#treeList");
                zTreeObj= $.fn.zTree.init(t, setting, response);
            }
        });


    }

    /**
     * 添加上级权限
     */
    function treeItemClick(treeId, treeNode){
        $("#pid" , parent.document).val(treeNode.id);
        $("#pidname" , parent.document).val(treeNode.name);
        layer_close();
    }


}();