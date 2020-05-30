package cc.liqingsong.database.enums;


import cc.liqingsong.common.enums.IErrorCode;

/**
 * User 的返回码
 *
 * @author liqingsong
 */
public enum UserCode implements IErrorCode {

    // ---错误返回码 2xxxx-----
    LOGIN_USERNAME_NOT_EMPTY(20001,"用户名不能为空"),
    LOGIN_PASSWORDS_NOT_EMPTY(20001,"密码不能为空"),
    LOGIN_USERNAME_PASSWORDS_ERR(20001,"用户或密码错误"),
    USERNAME_LENGTH_WORDS(20002,"用户名6-30个字符"),
    PASSWORDS_LENGTH_WORDS(20002,"密码6-15个字符"),
    LOGIN_IMGCODE_NOT_EMPTY(20003,"验证码不能为空"),
    LOGIN_IMGCODE_ERROR(20003,"验证码不正确");

    // 操作代码
    int code;

    // 提示信息
    String message;

    UserCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public String toString() {
        return String.format(" UserCode:{code=%s, msg=%s} ", code, message);
    }

}
