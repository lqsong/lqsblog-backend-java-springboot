package cc.liqingsong.database.enums;


import cc.liqingsong.common.enums.IErrorCode;

/**
 * TAG 的返回码
 *
 * @author liqingsong
 */
public enum TagCode implements IErrorCode {

    // ---错误返回码 7xxxx-----
    PINYIN_NOT_EMPTY(70001,"拼音不能为空"),
    PINYIN_LENGTH_WORDS(70002,"拼音1-60个字符"),
    NAME_LENGTH_WORDS(70003,"名称1-10个字");

    // 操作代码
    int code;

    // 提示信息
    String message;

    TagCode(int code, String message){
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
        return String.format(" TagCode:{code=%s, msg=%s} ", code, message);
    }

}
