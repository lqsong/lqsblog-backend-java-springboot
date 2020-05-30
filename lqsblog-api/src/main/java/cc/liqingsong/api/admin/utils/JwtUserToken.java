package cc.liqingsong.api.admin.utils;

import cc.liqingsong.common.utils.JwtUtils;
import cc.liqingsong.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户Token操作
 * @author liqingsong
 */
@Component
public class JwtUserToken {

    private JwtUtils jwtUtils;

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * 获取认证token
     *     user: 用户数据
     */
    public String getToken(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("locked",user.getLocked());
        String token = jwtUtils.createJWT(String.valueOf(user.getId()),user.getPassword(),map);
        return token;
    }

    /**
     * 根据过期时间与重置时间，重置认证token
     *     token: token字符串
     *     user: 用户数据
     */
    public String restToken(String token, User user) {
        Boolean aBoolean = jwtUtils.expirationNewJWT(token);
        if (aBoolean) {
            return getToken(user);
        }
        return null;
    }


}
