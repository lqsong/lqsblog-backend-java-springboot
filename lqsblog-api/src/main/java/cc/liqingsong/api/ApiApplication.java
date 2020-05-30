package cc.liqingsong.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Api Application
 * @author liqingsong
 */
@SpringBootApplication(scanBasePackages = {
        "cc.liqingsong.common",
        "cc.liqingsong.database",
        "cc.liqingsong.service",
        "cc.liqingsong.api"
})
@MapperScan("cc.liqingsong.database.mapper")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}




