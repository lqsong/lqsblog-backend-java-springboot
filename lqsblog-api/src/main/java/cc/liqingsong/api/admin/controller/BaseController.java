package cc.liqingsong.api.admin.controller;
import cc.liqingsong.api.admin.utils.JwtUserToken;
import cc.liqingsong.api.common.exception.ApiCommonException;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API-AdminBaseController
 * @author liqingsong
 */
public class BaseController {

    // Header头 Token 名称
    @Value("${lqsblog.jwt.header.tokenkey}")
    protected String tokenkey;

    protected JwtUserToken jwtUserToken;
    @Autowired
    public void setJwtUserToken(JwtUserToken jwtUserToken) {
        this.jwtUserToken = jwtUserToken;
    }

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected Long userId;

    protected String username;

    protected String nickname;

    protected Boolean locked;

    protected String newToken;

    @ModelAttribute
    public void setReqAnRes(HttpServletRequest request, HttpServletResponse response) throws ApiCommonException {
        this.request = request;
        this.response = response;

        // 获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        // 如果没有登录
        if(!subject.isAuthenticated()){
            throw new ApiCommonException(ResultCode.UNAUTHENTICATED);
        }
        // subject获取所有的安全集合
        PrincipalCollection principals = subject.getPrincipals();
        if (principals == null || principals.isEmpty()){
            throw new ApiCommonException(ResultCode.UNAUTHENTICATED);
        }
        // 获取安全数据
        User user = (User) principals.getPrimaryPrincipal();
        if(user.getLocked()) {
            throw new ApiCommonException(ResultCode.ACCOUNT_LOCKOUT);
        }

        // 赋值
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.locked = user.getLocked();

        // 设置是否生成新的token || null
        String token = request.getHeader(this.tokenkey);
        this.newToken = jwtUserToken.restToken(token, user);

    }


    public Page getPage() {
        long var1 = 1L;
        long var3 = 10L;
        String var5 = this.request.getParameter("page");
        if (!StringUtils.isEmpty(var5)) {
            Long var6 = Long.valueOf(var5);
            if (var6 > 0L) {
                var1 = var6;
            }
        }

        String var8 = this.request.getParameter("per");
        if (!StringUtils.isEmpty(var8)) {
            Long var7 = Long.valueOf(var8);
            if (var7 > 0L) {
                var3 = var7;
            }
        }

        return new Page(var1, var3);
    }


}
