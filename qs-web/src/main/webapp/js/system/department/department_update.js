var DepartmentUpdate = function () {
    var formId = $("#form_department_update");

    /**
     * 修改验证
     */
    function validatorFormUpdate() {
        var url = SERVER_CONTEXT + "/control/system/departmentManger/update";
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
                editUnique: true
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

    /**
     * 获取详细信息
     */
    function getModel() {
        var url = SERVER_CONTEXT + "/control/system/departmentManger/getModel";
        var data = {"id": id};
        utilGetModel(url, data, formId);
    }

    function updateFormShowPerTreeUi() {
        layer_show("上级部门", SERVER_CONTEXT + "/control/system/departmentManger/showTreeUi", "300", "", null);
    }

    function initBtnEvent() {
        formId.find("#pidName").unbind("click").bind("click", function () {
            updateFormShowPerTreeUi();
        });
        /**
         * 修改时唯一性验证
         */
        $.validator.addMethod("editUnique", function (value, element) {
            var url = SERVER_CONTEXT + "/control/system/departmentManger/noauth_verNo";
            var data = {"id": id};
            data[element.id] = value;
            return this.optional(element) || (utilCheckUnique(url, data, value));
        }, "已存在，请重新输入");
    }

    return {
        init: function () {
            getModel();
            validatorFormUpdate();
            initBtnEvent();
        }
    };
}();