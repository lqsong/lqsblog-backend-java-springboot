package cc.liqingsong.api.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Mvc 配置
 * @author liqingsong
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 静态资源访问路径 - application 中进行配置
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    // 文件上传目录绑定的自定义网址  - application 中进行配置
    @Value("${lqsblog.file.upload-weburl}")
    private String uploadWeburl;

    // 上传目录  - application 中进行配置
    @Value("${lqsblog.file.upload-dir}")
    private String uploadDrl;

    /**
     * @Description: 自定义资源映射，设置虚拟路径，访问绝对路径下资源
     */
     @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         // 如果没有设置 文件上传目录绑定的自定义网址，才自定义设置资源映射
         if(null == uploadWeburl || uploadWeburl.equals("")) {
             registry.addResourceHandler(staticPathPattern).addResourceLocations("file:///" + uploadDrl);
         }
    }
}
