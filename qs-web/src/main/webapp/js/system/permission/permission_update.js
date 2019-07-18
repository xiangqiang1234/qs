
var PermissionUpdate = function () {
    var form_update = $("#form_permission_update");

    /**
     * 修改验证
     */
    function validatorFormUpdate() {
        var url = SERVER_CONTEXT + "/control/system/permissionManger/update";
        var rules = {
            "permissionName": {
                required: true
            },
            "permissionSign": {
                required: true
            },
            "level": {
                required: true
            },
            "sortNo":{
                required: true,
                digits:true,
                min:0
            }
        };
        var messages = {
            "permissionName": {
                required: "请输入权限名称"
            },
            "permissionSign": {
                required: "请输入链接地址"
            },
            "level": {
                required: "请选择权限类型"
            },
            "sortNo":{
                required: "请输入排序号",
                digits:"序号只能输入整数",
                min:"序号只能输入大于0的整数"
            }
        };
        ValidatorUtil.validate(rules, messages, form_update, url);
    }

    /**
     * 获取详细信息
     * @param id
     */
    function getModel(id) {
        var url = SERVER_CONTEXT + "/control/system/permissionManger/getModel";
        var data = {"id": id};
        $.ajax({
            url: url,
            data: data,
            type: 'post',
            dataType: 'json',
            success: function (response) {
                if (response.success) {
                    var modelData = response.data;
                    form_update.fill(modelData);
                } else {
                    layer.alert(response.message, {icon: 0,title:'提示',closeBtn:0},function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.$('#refreshBtn').click();
                        parent.layer.close(index);
                    });

                }
            }
        });
    }

    function updateFormShowPerTreeUi(){

        layer_show("上级权限", SERVER_CONTEXT + "/control/system/permissionManger/addFormShowPerTreeUi", "300", "",null);
    }

    function initBtnEvent(){
        $("#pidname").unbind("click").bind("click", function () {
            updateFormShowPerTreeUi();
        });
    }

    return {
        init: function () {
            getModel(id);
            validatorFormUpdate();
            initBtnEvent();
        },
        updateFormShowPerTreeUi:updateFormShowPerTreeUi
    };
}();