var DepartmentAdd = function () {
    var formId = $("#form_department_add");

    /**
     * 保存验证
     */
    function validatorFormAdd() {
        var url = SERVER_CONTEXT + "/control/system/departmentManger/add";
        var rules = {
            "name": {
                required: true,
                maxlength: 15
            },
            "no": {
                required: true,
                maxlength: 6,
                minlength: 6,
                digits: true,
                addUnique: true
            },
            "phone": {
                required: true,
                isTel: true
            }
        };
        var messages = {
            "no": {
                maxlength: "只能输入6个数字",
                minlength: "只能输入6个数字"
            }
        };
        ValidatorUtil.validate(rules, messages, formId, url);
    }

    function bindEvent() {
        formId.find("#pid").val(pid);
        formId.find("#pSerialNumber").val(pSerialNumber);
        formId.find("#pidName").val(pName);

        /**
         * 新增时唯一性验证
         */
        $.validator.addMethod("addUnique", function (value, element) {
            var url = SERVER_CONTEXT + "/control/system/departmentManger/noauth_verNo";
            var data = {"id": ""};
            data[element.id] = value;
            return this.optional(element) || (utilCheckUnique(url, data, value));
        }, "已存在，请重新输入");
        formId.find("#pidName").unbind("click").bind("click", function () {
            addFormShowPerTreeUi();
        });
    }

    function addFormShowPerTreeUi() {
        layer_show("上级部门", SERVER_CONTEXT + "/control/system/departmentManger/showTreeUi", "400", "", null);
    }

    return {
        init: function () {
            bindEvent();
            validatorFormAdd();
        }
    };
}();