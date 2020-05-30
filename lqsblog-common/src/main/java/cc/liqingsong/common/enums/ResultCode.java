package cc.liqingsong.common.enums;

/**
 * 公共的返回码
 *
 *    返回码code：
 *      成功：0
 *      失败：10001
 *      未登录：10002
 *      未授权：10003
 *      抛出异常：99999
 * @author liqingsong
 */
public enum ResultCode implements IErrorCode{

    SUCCESS(0,"操作成功！"),

    // ---系统错误返回码-----
    NOT_FOUND(404,"资源不存在"),
    FAIL(10001,"操作失败"),
    UNAUTHENTICATED(10002,"您还未登录"),
    UNAUTHORISE(10003,"权限不足"),
    ACCOUNT_LOCKOUT(10004,"账号锁定"),
    INCORRECT_PARAMETER(10005,"参数不正确"),
    // ---数据库统一错误返回码-----
    ID_REQUIRED(19000, "主键 ID 必须存在"),
    ID_NOT_FOUND(19000, "主键 ID 数据不存在"),
    PID_REQUIRED(19001, "父 ID 必须存在"),
    PID_NOT_FOUND(19001, "父 ID 数据不存在"),
    NAME_NOT_EMPTY(19002,"名称不能为空"),
    NAME_THE_SAME(19002,"存在相同名称"),
    TITLE_NOT_EMPTY(19002,"标题不能为空"),
    TITLE_LENGTH_WORDS(19003,"Title最长30个字"),
    KEYWORDS_LENGTH_WORDS(19004,"Keywords最长50个字"),
    DESCRIPTION_LENGTH_WORDS(19005,"Description最长200个字"),
    SAVE_DATA_ERR(19005,"插入数据失败"),
    UNABLE_TO_DEL_HAVING_CHILD(19006,"有子元素无法删除，请先删除子元素"),
    // ---服务器错误返回码-----
    SERVER_ERROR(99999,"抱歉，系统繁忙，请稍后重试！");

    // 操作代码
    int code;

    // 提示信息
    String message;

    ResultCode(int code, String message){
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
        return String.format(" ResultCode:{code=%s, msg=%s} ", code, message);
    }

}
