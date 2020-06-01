package cc.liqingsong.database.enums;

import cc.liqingsong.common.enums.IErrorCode;

public enum TopicsCode implements IErrorCode {
    // ---错误返回码 5xxxx-----
    ALIAS_NOT_EMPTY(50001,"别名不能为空"),
    ALIAS_THE_SAME(50001,"存在相同别名"),
    ALIAS_LENGTH_WORDS(50001,"别名1-10个字符"),
    TITLE_LENGTH_WORDS(50002,"标题3-50个字"),
    ADDTIME_NOT_EMPTY(50003,"发布时间不能为空");

    // 操作代码
    int code;

    // 提示信息
    String message;

    TopicsCode(int code, String message){
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
        return String.format(" TopicsCode:{code=%s, msg=%s} ", code, message);
    }
}
