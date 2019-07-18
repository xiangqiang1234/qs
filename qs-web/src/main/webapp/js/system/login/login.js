/**
 * @authorCreated by xq on 2017/12/23.
 */
var Login = function () {
    /**
     * 登陆验证
     */
    function validatorFormUserLogin() {
        $('#loginForm').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },

            messages: {
                username: {
                    required: "用户名不能为空"
                },
                password: {
                    required: "密码不能为空"
                }
            },

            errorPlacement: function (error, element) {
                layer.tips(error.get(0).textContent, '#' + element.get(0).id, {
                    tipsMore: true
                });
            },

            submitHandler: function (form) {
                form.submit();
            }
        });
    }

    return {
        init: function () {
            validatorFormUserLogin();
        },
    };
}();