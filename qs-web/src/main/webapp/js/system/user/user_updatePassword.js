
var UserUpdatePassword = function () {
    var form_update = $("#form_user_updatePassword");
    /**
     * 绑定事件
     */
    function bindEvent() {
        /**
         * 验证旧密码是否正确
         */
        $.validator.addMethod("checkPassword", function (value, element) {
            var url = SERVER_CONTEXT + "/control/system/userManger/checkPassword";
            var data = {"password": value};
            return this.optional(element) || (utilCheckUnique(url, data, value));
        }, "密码错误");
    }

    /**
     * 修改验证
     */
    function validatorFormUpdate() {
        var url = SERVER_CONTEXT + "/control/system/userManger/updatePassword";
        var rules = {
            "oldPassword": {
                required: true,
                checkPassword:true
            },
            "newPassword": {
                required: true,
                maxlength: 16,
                minlength: 6
            },
            "newPassword2": {
                equalTo: "#newPassword"
            }
        };
        var messages = {};
        ValidatorUtil.validate(rules, messages, form_update, url);
    }

    return {
        init: function () {
            bindEvent();
            validatorFormUpdate();
        }
    };
}();