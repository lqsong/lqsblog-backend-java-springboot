package cc.liqingsong.common.exception;

import cc.liqingsong.common.enums.IErrorCode;

/**
 * 自定义公共运行异常
 * @author liqingsong
 */
public class CommonRuntimeException extends RuntimeException {

    private Integer code;

    private String msg;

    public CommonRuntimeException(String message) {
        super(message);
    }

    public CommonRuntimeException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public CommonRuntimeException(IErrorCode iErrorCode) {
        super(iErrorCode.getMessage());
        this.code = iErrorCode.getCode();
        this.msg = iErrorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }
}
