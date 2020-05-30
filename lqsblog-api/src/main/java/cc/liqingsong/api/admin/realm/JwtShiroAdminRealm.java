package cc.liqingsong.api.admin.realm;

import cc.liqingsong.common.utils.JwtUtils;
import cc.liqingsong.database.entity.Permission;
import cc.liqingsong.database.entity.User;
import cc.liqingsong.service.admin.PermissionService;
import cc.liqingsong.service.admin.UserService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtShiroAdminRealm extends AuthorizingRealm {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    private PermissionService permissionService;
    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }



    protected JwtUtils jwtUtils;
    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public void setName(String name) {
        super.setName("jwtShiroAdminRealm");
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     * 授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1、 获取已认证的用户数据
        User user = (User) principalCollection.getPrimaryPrincipal();
        // 2、查询数据库用户的权限
        List<Permission> permissions = permissionService.listPermissionByUserId(user.getId());
        // 3、根据用户数据获取用户的权限信息（所有角色，所有权限）
        Set<String> perms = new HashSet<>(); // 所有权限
        for (Permission item: permissions ) {
            perms.add(item.getPermission());
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);
        return info;
    }

    /**
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1、获取登录的用户名密码(token)
        String token = (String) authenticationToken.getCredentials();
        String userid = null;
        String password = null;
        if (token == null) {
            throw new NullPointerException("jwtToken 不允许为空");
        }
        try {
            Claims claims = jwtUtils.parseJWT(token);
            userid = claims.getId();
            password = claims.getSubject();
        }catch (Exception e) {
            //e.printStackTrace();
           throw new RuntimeException("未登陆");
        }
        // 2、查询数据库
        User user = userService.getById(userid);
        // 3、判断用户是否存在，并密码是否正确
        if(user != null) {
            // 加密密码
            if (user.getPassword().equals(password)) {
                // 4、如果一致返回安全数据
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token,this.getName());
                return info;
            }
        }
        // 5、不一致,返回null(抛出异常)
        return null;
    }
}
