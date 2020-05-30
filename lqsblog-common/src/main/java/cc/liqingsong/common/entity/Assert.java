package cc.liqingsong.common.entity;


import cc.liqingsong.common.enums.IErrorCode;
import cc.liqingsong.common.exception.CommonRuntimeException;


/**
 * 自定义Api断言
 * @author liqingsong
 */
public class Assert {

    protected Assert() {
        // to do noting
    }


    public static void fail(String message){
        fail(10000, message);
    }

    public static void fail(Integer code, String message){
        throw new CommonRuntimeException(code, message);
    }

    public static void fail(boolean condition, String message){
        if (condition) {
            fail(message);
        }
    }

    public static void fail(boolean condition, String message, Integer code){
        if (condition) {
            fail(code, message);
        }
    }


    public static void fail(IErrorCode iErrorCode){
        throw new CommonRuntimeException(iErrorCode);
    }

    public static void fail(boolean condition, IErrorCode iErrorCode){
        if (condition) {
            fail(iErrorCode);
        }
    }




}
