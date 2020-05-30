package cc.liqingsong.webs.common.error;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
/**
 * WEB 重置默认错误
 * @author liqingsong
 */
// @Component // 默认系统，不用自定义
public class WebErrorAttributes extends DefaultErrorAttributes {


    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        Map<String,Object> error = (Map<String, Object>) webRequest.getAttribute("error", 0);
        map.putAll(error);
        return map;
    }
}
