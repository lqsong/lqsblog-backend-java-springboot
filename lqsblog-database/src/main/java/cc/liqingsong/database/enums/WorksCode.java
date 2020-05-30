package cc.liqingsong.database.enums;

import cc.liqingsong.common.enums.IErrorCode;

public enum WorksCode implements IErrorCode {
    // ---错误返回码 4xxxx-----
    TITLE_LENGTH_WORDS(40001,"标题5-100个字"),
    ADDTIME_NOT_EMPTY(40002,"发布时间不能为空");

    // 操作代码
    int code;

    // 提示信息
    String message;

    WorksCode(int code, String message){
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
        return String.format(" WorksCode:{code=%s, msg=%s} ", code, message);
    }
}
