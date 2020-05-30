package cc.liqingsong.database.enums;

import cc.liqingsong.common.enums.IErrorCode;

public enum LinkCode implements IErrorCode {
    // ---错误返回码 6xxxx-----
    CATEGORY_NAME_LENGTH_WORDS(60001,"名称1-8个字"),
    CATEGORY_ALIAS_NOT_EMPTY(60002,"别名不能为空"),
    CATEGORY_ALIAS_THE_SAME(60002,"存在相同别名"),
    CATEGORY_ALIAS_LENGTH_WORDS(60002,"别名1-10个字符"),
    CATEGORY_ID_NOT_EMPTY(60003,"分类ID不能为空"),
    CATEGORY_ID_LINK_DATA(60004,"分类下有链接，请先删除分类下链接"),
    TITLE_LENGTH_WORDS(60005,"标题5-50个字"),;

    // 操作代码
    int code;

    // 提示信息
    String message;

    LinkCode(int code, String message){
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
        return String.format(" LinkCode:{code=%s, msg=%s} ", code, message);
    }
}
