<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<aside class="Hui-aside">
    <div class="menu_dropdown bk_2">
        <%--系统管理--%>
        <shiro:hasPermission name="/control/system">
            <dl id="menu-admin">
                <dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
                </dt>
                <dd>
                    <ul>
                        <shiro:hasPermission name="/control/system/departmentManger/ui">
                            <li><a data-href="/control/system/departmentManger/ui" data-title="部门管理">部门管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/control/system/role/ui">
                            <li><a data-href="/control/system/role/ui" data-title="角色管理">角色管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/control/system/permissionManger/ui">
                            <li><a data-href="/control/system/permissionManger/ui" data-title="权限管理">权限管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/control/system/userManger/ui">
                            <li><a data-href="/control/system/userManger/ui" data-title="用户管理">用户管理</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/control/system/dictionary/ui">
                            <li><a data-href="/control/system/dictionary/ui" data-title="字典管理">字典管理</a></li>
                        </shiro:hasPermission>
                    </ul>
                </dd>
            </dl>
        </shiro:hasPermission>

    </div>
</aside>
