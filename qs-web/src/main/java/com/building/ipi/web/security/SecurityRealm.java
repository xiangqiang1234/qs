package com.building.ipi.web.security;

import java.util.List;
import javax.annotation.Resource;
import com.building.ipi.web.system.permission.model.SysPermission;
import com.building.ipi.web.system.permission.service.PermissionService;
import com.building.ipi.web.system.role.model.Role;
import com.building.ipi.web.system.role.service.RoleService;
import com.building.ipi.web.system.user.model.SysUser;
import com.building.ipi.web.system.user.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;


/**
 * 用户身份验证,授权 Realm 组件
 *
 * @author StarZou
 * @since 2014年6月11日 上午11:35:28
 **/
@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    /**
     * 权限检查
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = String.valueOf(principals.getPrimaryPrincipal());
        final SysUser user = userService.selectByUsername(username);
        final List<Role> roleInfos = roleService.selectRolesByUserId(user.getId());
        for (Role role : roleInfos) {
            // 添加角色
            authorizationInfo.addRole(role.getRoleSign());

            final List<SysPermission> permissions;
            if("admin".equals(role.getRoleSign())){
                permissions = permissionService.selectAllPermissions();
            }else{
                permissions = permissionService.selectPermissionsByRoleId(role.getId());
            }
            for (SysPermission permission : permissions) {
                // 添加权限
                authorizationInfo.addStringPermission(permission.getPermissionSign());
            }
        }
        return authorizationInfo;
    }

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = String.valueOf(token.getPrincipal());
        String password = new String((char[]) token.getCredentials());
        // 通过数据库进行验证
        SysUser user=new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        final SysUser authentication = userService.authentication(user);
        if (authentication == null) {
            throw new AuthenticationException("用户名或密码错误.");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        return authenticationInfo;
    }

}
