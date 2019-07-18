
var RoleAdd = function () {
    var form_add = $("#form_role_add");

    /**
     * 保存验证
     */
    function validatorFormAdd() {
        var url = SERVER_CONTEXT + "/control/system/role/add";
        var rules = {
            "roleName": {
                required: true
            },
            "roleSign": {
                required: true,
                addUnique: true
            }
        };
        var messages = {
            "roleName": {
                required: "请输入角色名称"
            },
            "roleSign": {
                required: "请输入唯一标识",
                addUnique: "唯一标识已存在，请重新输入"
            }
        };
        ValidatorUtil.validate(rules, messages, form_add, url);
    }

    /**
     * 绑定事件
     */
    function bindEvent() {
        /**
         * 新增时唯一性验证
         */
        $.validator.addMethod("addUnique", function (value, element) {
            var url = SERVER_CONTEXT + "/control/system/role/roleUnique";
            var data = {"id": ""};
            data[element.id] = value;
            return this.optional(element) || (utilCheckUnique(url, data, value));
        }, "");
    }

    return {
        init: function () {
            validatorFormAdd();
            bindEvent();
        }
    };
}();