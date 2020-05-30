package cc.liqingsong.webs.controller;

import cc.liqingsong.webs.common.exception.WebCommonException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Home
 * @author liqingsong
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "home";
    }

    @RequestMapping("/err")
    public String error(@RequestParam("name") String name) throws Exception{
        if (StringUtils.isEmpty(name)) {
            throw new WebCommonException(201, "测试错误");
        }
        return "hello";
    }
}
