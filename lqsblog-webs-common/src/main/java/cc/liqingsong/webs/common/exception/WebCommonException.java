package cc.liqingsong.webs.common.exception;

/**
 * WEB 自定义公共异常
 * @author liqingsong
 */
public class WebCommonException extends Exception {

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public WebCommonException(int code, String message) {
        super(message);

        this.code = code;
        this.msg = message;

    }


}
