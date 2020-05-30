package cc.liqingsong.database.enums;

import cc.liqingsong.common.enums.IErrorCode;

public enum PermissionCode implements IErrorCode {
    // ---错误返回码 1xxxx-----
    TITLE_LENGTH_WORDS(10101,"标题1-8个字"),
    PERMISSION_LENGTH_WORDS(10102,"权限编号最长100个字");

    // 操作代码
    int code;

    // 提示信息
    String message;

    PermissionCode(int code, String message){
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
        return String.format(" PermissionCode:{code=%s, msg=%s} ", code, message);
    }
}
