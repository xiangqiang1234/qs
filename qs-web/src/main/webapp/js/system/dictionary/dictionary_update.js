
var DictionaryUpdate = function () {
    var form_update = $("#form_dictionary_update");

    /**
     * 修改验证
     */
    function validatorFormUpdate() {
        var url = SERVER_CONTEXT + "/control/system/dictionary/update";
        var rules = {
            "name" : {
                required : true,
                maxlength : 50
            },
            "code" : {
                required : true,
                maxlength : 50,
                editUnique:true
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
                editUnique:"编码已存在"
            },
            "isactive" : {
                required : "请选择启用禁用"
            }
        };
        ValidatorUtil.validate(rules, messages, form_update, url);
    }

    /**
     * 获取详细信息
     * @param id
     */
    function getModel(id) {
        var url = SERVER_CONTEXT + "/control/system/dictionary/getModel";
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

    function updateFormShowPerTreeUi(){

        layer_show("上级字典", SERVER_CONTEXT + "/control/system/dictionary/showTreeUi", "300", "",null);
    }

    function initBtnEvent(){
        $("#pidname").unbind("click").bind("click", function () {
            updateFormShowPerTreeUi();
        });
        /**
         * 修改时唯一性验证
         */
        $.validator.addMethod("editUnique", function (value, element) {
            var id = form_update.find("#id").val();
            return this.optional(element) || (checkUnique(element.id, value, id));
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

    return {
        init: function () {
            getModel(id);
            validatorFormUpdate();
            initBtnEvent();
        },
        updateFormShowPerTreeUi:updateFormShowPerTreeUi
    };
}();