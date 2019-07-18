
var DictionaryAdd = function () {
    var form_add = $("#form_dictionary_add");

    /**
     * 保存验证
     */
    function validatorFormAdd() {
        var url = SERVER_CONTEXT + "/control/system/dictionary/add";
        var rules = {
            "name" : {
                required : true,
                maxlength : 50
            },
            "code" : {
                required : true,
                maxlength : 50,
                addUnique:true
            },
            "isactive" : {
                required : true
            }
        };
        var messages = {
            "name" : {
                required : "请填写字典名称",
                maxlength : "名称不能超过50个字符"
            },
            "code" : {
                required : "请填写编码",
                maxlength : "编码不能超过50字符",
                addUnique:"编码已存在"
            },
            "isactive" : {
                required : "请选择启用禁用"
            }
        };
        ValidatorUtil.validate(rules, messages, form_add, url);

    }

    function bindEvent(){
        /**
         * 新增时唯一性验证
         */
        $.validator.addMethod("addUnique", function (value, element) {
            return this.optional(element) || (checkUnique(element.id, value, ""));
        }, "");
    }

    /**
     * 唯一性验证方法
     * @param name
     * @param value
     * @param id
     * @returns {boolean}
     */
    function checkUnique(name, value, id) {
        var returnFlag = true;
        var data = {"id": id};
        data[name]=value;
        if (value != undefined && value != "") {
            var url = SERVER_CONTEXT + "/control/system/dictionary/noauth_verCode";
            $.ajax({
                url: url,
                data: data,
                type: 'post',
                async: false,
                dataType: 'json',
                success: function (data) {
                    returnFlag = data.success;
                }
            });
        }
        return returnFlag;
    }



    function addFormShowPerTreeUi(){
        layer_show("上级字典", SERVER_CONTEXT + "/control/system/dictionary/showTreeUi", "300", "",null);
    }

    return {
        init: function () {
            bindEvent();
            validatorFormAdd();
        },
        addFormShowPerTreeUi:addFormShowPerTreeUi
    };
}();