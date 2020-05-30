package cc.liqingsong.api.common.handler;

import cc.liqingsong.common.entity.Result;
import cc.liqingsong.api.common.exception.ApiCommonException;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.common.exception.CommonRuntimeException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API 自定义的公共异常处理器
 *      1.声明异常处理器
 *      2.对异常统一处理
 * @author liqingsong
 */
@ControllerAdvice
public class BaseExceptionHandler {


    /**
     * 异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code= HttpStatus.OK)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        // System.out.println("进入异常");
        if(e.getClass() == ApiCommonException.class) {
            //类型转型
            ApiCommonException ce = (ApiCommonException) e;
            Result result = new Result(ce.getIErrorCode());
            return result;
        } else if (e.getClass() == CommonRuntimeException.class){
            CommonRuntimeException ce = (CommonRuntimeException) e;
            Result result = new Result(ce.getCode(),ce.getMessage());
            return result;
        } else {
            System.out.println(e);
            Result result = new Result(ResultCode.SERVER_ERROR);
            return result;
        }
    }


    /**
     * 权限异常-未授权
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseStatus(code= HttpStatus.OK)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response,AuthorizationException e) {
        return new Result(ResultCode.UNAUTHORISE);
    }


    /**
     * 404错误，处理器不存在异常，资源不存在异常
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(code= HttpStatus.OK)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response, NoHandlerFoundException e) {
        return new Result(ResultCode.NOT_FOUND);
    }



}