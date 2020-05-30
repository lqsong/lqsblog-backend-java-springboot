package cc.liqingsong.webs.common.handler;

import cc.liqingsong.webs.common.exception.WebCommonException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * WEB 自定义的公共异常处理器
 *      1.声明异常处理器
 *      2.对异常统一处理
 * @author liqingsong
 */
// @ControllerAdvice // 默认系统，不用自定义
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        int code = 99999;
        String msg = "抱歉，系统繁忙，请稍后重试！";
        if(e.getClass() == WebCommonException.class) {
            WebCommonException ex = (WebCommonException) e;
            code = ex.getCode();
            msg = ex.getMsg();
        }
        String accept = request.getHeader("accept");
        if(accept.startsWith("text/html")) {
            request.setAttribute("javax.servlet.error.status_code",500); // 不设置默认 200
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        request.setAttribute("error",map);
        return "forward:/error";
    }

}
