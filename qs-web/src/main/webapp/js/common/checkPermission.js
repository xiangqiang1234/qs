/**
 * 用户权限检查 JS
 *
 * @author yuzj 2018-03-23
 */
var CheckPermission = function () {
    var permissionSignList = [];
    return {
        init: function () {
            //获取权限数据
            getPermissionList();
        },
        hasPermission: hasPermission,
        checkSession: checkSession
    };

    /**
     * 获取权限数据
     */
    function getPermissionList() {
        permissionSignList = [];
        var url = SERVER_CONTEXT + "/control/system/permissionManger/getPermissionList";
        $.ajax({
            url: url,
            data: null,
            type: 'post',
            async: false,
            dataType: 'json',
            success: function (response) {
                var hasPermissionList = response.data;
                for (var i = 0; i < hasPermissionList.length; i++) {
                    permissionSignList.push(hasPermissionList[i].permissionSign);
                }
            }
        });
    }

    /**
     * 判断是否拥有权限
     * @param url
     */
    function hasPermission(url) {
        if (permissionSignList) {
            if (permissionSignList.indexOf(url) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证session是否失效
     */
    function checkSession() {
        var flag = true;
        $.ajax({
            url: SERVER_CONTEXT + "/control/system/permissionManger/checkPermission",
            type: 'post',
            async: false,
            dataType: 'json',
            success: function (response) {
                if (!response.success) {
                    layer.alert("登录超时，请重新登录！", {icon: 0, title: '提示', closeBtn: 0}, function () {
                        window.parent.location.href = SERVER_CONTEXT + "/control/user/logout";
                    });
                    flag = false;
                }
            },
            error: function () {
                layer.alert("登录超时，请重新登录！", {icon: 0, title: '提示', closeBtn: 0}, function () {
                    window.parent.location.href = SERVER_CONTEXT + "/control/user/logout";
                });
                flag = false;
            }
        });
        return flag;
    }

}();
