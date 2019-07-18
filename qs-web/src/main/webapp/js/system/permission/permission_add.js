
var PermissionAdd = function () {
    var form_add = $("#form_permission_add");

    /**
     * 保存验证
     */
    function validatorFormAdd() {
        var url = SERVER_CONTEXT + "/control/system/permissionManger/add";
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
        ValidatorUtil.validate(rules, messages, form_add, url);
    }

    function addFormShowPerTreeUi(){
        layer_show("上级权限", SERVER_CONTEXT + "/control/system/permissionManger/addFormShowPerTreeUi", "350", "",null);
    }

    return {
        init: function () {
            validatorFormAdd();
        },
        addFormShowPerTreeUi:addFormShowPerTreeUi
    };
}();