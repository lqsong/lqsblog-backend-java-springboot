package cc.liqingsong.webs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * WEB Application
 * @author liqingsong
 */
@SpringBootApplication(scanBasePackages = {
        "cc.liqingsong.common",
        "cc.liqingsong.database",
        "cc.liqingsong.service.pc",
        "cc.liqingsong.webs"
})
@MapperScan("cc.liqingsong.database.mapper")
public class WebsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsApplication.class, args);
    }
}




