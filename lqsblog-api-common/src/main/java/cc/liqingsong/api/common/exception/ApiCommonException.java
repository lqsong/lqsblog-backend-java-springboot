package cc.liqingsong.api.common.exception;

import cc.liqingsong.common.enums.IErrorCode;

/**
 * API 自定义公共异常
 * @author liqingsong
 */
public class ApiCommonException extends Exception {

    private IErrorCode iErrorCode;

    public ApiCommonException(String message) {
        super(message);
    }

    public ApiCommonException(IErrorCode iErrorCode) {
        super(iErrorCode.getMessage());
        this.iErrorCode = iErrorCode;
    }

    public IErrorCode getIErrorCode() {
        return iErrorCode;
    }
}
