package cc.liqingsong.api.admin.interceptor;

import cc.liqingsong.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Admin Jwt 拦截器
 * @author liqingsong
 */
// @Component // ApiAdminAppConfig 中自定义手动配置
public class JwtAdminHandlerInterceptor implements HandlerInterceptor {

    // Header头 Token 名称
    @Value("${lqsblog.jwt.header.tokenkey}")
    private String tokenkey;

    private JwtUtils jwtUtils;

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    // 目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        System.out.println("拦截器");
        int status = response.getStatus();
        System.out.println(status);
        System.out.println(request.getRequestURI());
        */
        // 1、通过 request 获取请求的 token 信息
        String token = request.getHeader(tokenkey);
        if (!StringUtils.isEmpty(token)) {

            Claims claims = jwtUtils.parseJWT(token);
            if(claims != null) {
                request.setAttribute(tokenkey, claims);
                return true;
            }

        }
        // 未登录
        request.getRequestDispatcher("/common/err?code=10002").forward(request,response);
        return false;
        // throw new ApiCommonException(ResultCode.UNAUTHENTICATED);

    }

    // 目标方法执行之后
    /*@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }*/

    // 响应结束之前执行
    /*@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }*/
}
