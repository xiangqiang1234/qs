
var RoleUpdate = function () {
    var form_update = $("#form_role_update");

    /**
     * 修改验证
     */
    function validatorFormUpdate() {
        var url = SERVER_CONTEXT + "/control/system/role/update";
        var rules = {
            "roleName": {
                required: true
            },
            "roleSign": {
                required: true,
                editUnique: true
            }
        };
        var messages = {
            "roleName": {
                required: "请输入角色名称"
            },
            "roleSign": {
                required: "请输入唯一标识",
                editUnique: "唯一标识已存在，请重新输入"
            }
        };
        ValidatorUtil.validate(rules, messages, form_update, url);
    }

    /**
     * 获取详细信息
     * @param id
     */
    function getModel(id) {
        var url = SERVER_CONTEXT + "/control/system/role/getModel";
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

    /**
     * 绑定事件
     */
    function bindEvent() {
        /**
         * 修改时唯一性验证
         */
        $.validator.addMethod("editUnique", function (value, element) {
            var url = SERVER_CONTEXT + "/control/system/role/roleUnique";
            var data = {"id": id};
            data[element.id] = value;
            return this.optional(element) || (utilCheckUnique(url, data, value));
        }, "");
    }
    return {
        init: function () {
            getModel(id);
            validatorFormUpdate();
            bindEvent();
        }
    };
}();