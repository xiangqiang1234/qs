/**
 * Created by xq on 2017/12/19.
 */
var UserAdd = function () {
    var formId = $("#form_user_add");

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
         * 新增时唯一性验证
         */
        $.validator.addMethod("addUnique", function (value, element) {
            var url = SERVER_CONTEXT + "/control/system/userManger/userUnique";
            var data = {"id": ""};
            data[element.id] = value;
            return this.optional(element) || (utilCheckUnique(url, data, value));
        }, "");

        formId.find("#departmentName").unbind("click").bind("click", function () {
            addFormShowPerTreeUi();
        });
        getRoleList();
    }

    function addFormShowPerTreeUi(){
        layer_show("上级部门", SERVER_CONTEXT + "/control/system/departmentManger/showTreeUi?pageId=user", "410", "",null);
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
     * 添加验证
     */
    function validatorFormUserAdd() {
        var url = SERVER_CONTEXT + "/control/system/userManger/addUser";
        var rules = {
            "username": {
                required: true,
                addUnique: true,
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
                addUnique: "用户名已存在，请重新输入"
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

    return {
        init: function () {
            bindEvent();
            validatorFormUserAdd();
        }
    };
}();