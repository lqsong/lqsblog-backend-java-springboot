package cc.liqingsong.api.admin.realm;

import cc.liqingsong.database.entity.User;
import cc.liqingsong.service.admin.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Admin ShiroRealm
 * @author liqingsong
 */
public class ShiroAdminRealm extends AuthorizingRealm {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setName(String name) {
        super.setName("shiroAdminRealm");
    }

    /**
     * 授权方法
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1、 获取已认证的用户数据
        User user = (User) principalCollection.getPrimaryPrincipal();
        // 2、根据用户数据获取用户的权限信息（所有角色，所有权限）
        // Set<String> roles = new HashSet<>(); // 所有角色
        Set<String> perms = new HashSet<>(); // 所有权限
        /*for(Role role : user.getRoles) {
            roles.add(role.getName());
            for (Permission perm : role.getPermissions()) {
                perms.add(perm.getCode());
            }
        }*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);
        // info.setRoles(roles);
        return info;
    }

    /**
     * 认证方法
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1、获取登录的用户名密码(token)
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        // 2、查询数据库
        User user = userService.selectByUserName(username);
        // 3、判断用户是否存在，并密码是否正确
        if(user != null) {
            // 加密密码
            password = new Md5Hash(password,user.getSalt(),3).toString();
            if (user.getPassword().equals(password)) {
                // 4、如果一致返回安全数据
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, upToken.getPassword(),this.getName());
                return info;
            }
        }
        // 5、不一致,返回null(抛出异常)
        return null;
    }
}
