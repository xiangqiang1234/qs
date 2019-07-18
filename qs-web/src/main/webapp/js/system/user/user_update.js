var UserUpdate = function () {
    var formId = $("#form_user_update");

    /**
     * 下拉框初始化
     * @returns
     */
    function selectInit() {
        /*角色下拉框*/
        getRoleList();
    }

    /**
     * 绑定事件
     */
    function bindEvent() {
        /**
         * 手机号验证方法
         */
        $.validator.addMethod("checkPhone", function (value, element, params) {
            var phone = /^1[3|5|8]\d{9}$/;
            if (value == "") {
                return true;
            } else {
                return phone.test(value);
            }
        }, "请输入正确的电话号码");
        /**
         * 修改时唯一性验证
         */
        $.validator.addMethod("editUnique", function (value, element) {
            var id = formId.find("#id").val();
            var url = SERVER_CONTEXT + "/control/system/userManger/userUnique";
            var data = {"id": id};
            data[element.id] = value;
            return this.optional(element) || (utilCheckUnique(url, data, value));
        }, "");

        formId.find("#departmentName").unbind("click").bind("click", function () {
            addFormShowPerTreeUi();
        });
    }

    function addFormShowPerTreeUi() {
        layer_show("上级部门", SERVER_CONTEXT + "/control/system/departmentManger/showTreeUi?pageId=user", "400", "", null);
    }

    /**
     * 获取角色列表
     */
    function getRoleList() {
        var url = SERVER_CONTEXT + "/control/system/role/getRoleList";
        $.ajax({
            url: url,
            type: 'post',
            async: false,
            dataType: 'json',
            success: function (data) {
                var content = "";
                for (var i = 0; i < data.length; i++) {
                    content += "<option value='" + data[i].id + "'>" + data[i].roleName + "</option>";
                }
                formId.find("#roleId").append(content);
            }
        });
    }

    /**
     * 修改验证
     */
    function validatorFormUpdate() {
        var url = SERVER_CONTEXT + "/control/system/userManger/update";
        var rules = {
            "username": {
                required: true,
                editUnique: true,
                maxlength:20
            },
            "name": {
                required: true,
                maxlength:20
            },
            "phone": {
                checkPhone: true
            },
            "email": {
                email: true,
                maxlength:50
            },
            "roleId": {
                required: true
            },
            "departmentId": {
                required: true
            }
        };
        var messages = {
            "username": {
                required: "请输入用户名",
                editUnique: "用户名已存在，请重新输入"
            },
            "name": {
                required: "请输入姓名"
            },
            "roleId": {
                required: "请选择角色"
            },
            "departmentId": {
                required: "请选择单位"
            }
        };
        ValidatorUtil.validate(rules, messages, formId, url);
    }

    /**
     * 获取详细信息
     */
    function getModel() {
        var url = SERVER_CONTEXT + "/control/system/userManger/getModel";
        var data = {"id": id};
        $.ajax({
            url: url,
            data: data,
            type: 'post',
            dataType: 'json',
            success: function (response) {
                if (response.success) {
                    var modelData = response.data;
                    formId.fill(modelData);
                    formId.find("#type" + modelData.type).click();
                } else {
                    layer.alert(response.message, {icon: 0, title: '提示', closeBtn: 0}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.$('#refreshBtn').click();
                        parent.layer.close(index);
                    });

                }
            }
        });
    }

    return {
        init: function () {
            selectInit();
            bindEvent();
            getModel();
            validatorFormUpdate();
        }
    };
}();