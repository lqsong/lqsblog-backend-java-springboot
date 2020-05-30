package cc.liqingsong.api.common.controller;

import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * API - 错误Controller
 * @author liqingsong
 */
@RestController
@RequestMapping("/common")
public class ErrorController {

    @RequestMapping("/err")
    public Result err(@RequestParam("code") Integer code) {
        Result result = new Result(ResultCode.SERVER_ERROR);
        switch(code){
            case 10002 : // 您还未登录
                result = new Result(ResultCode.UNAUTHENTICATED);
                break;
            case 10003 : // 权限不足
                result = new Result(ResultCode.UNAUTHORISE);
                break;
            default :
        }
        return result;
    }

}
