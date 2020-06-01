package cc.liqingsong.database.enums;

import cc.liqingsong.common.enums.IErrorCode;

public enum ArticleCode implements IErrorCode {
    // ---错误返回码 3xxxx-----
    CATEGORY_NAME_LENGTH_WORDS(30001,"名称1-8个字"),
    CATEGORY_ALIAS_NOT_EMPTY(30002,"别名不能为空"),
    CATEGORY_ALIAS_THE_SAME(30002,"存在相同别名"),
    CATEGORY_ALIAS_LENGTH_WORDS(30003,"别名1-10个字符"),
    CATEGORY_ID_LINK_DATA(30003,"分类下有数据，请先删除分类下数据"),
    TITLE_LENGTH_WORDS(30004,"标题3-100个字"),
    CATEGORY_ID_NOT_EMPTY(30005,"分类ID不能为空"),
    ADDTIME_NOT_EMPTY(30005,"发布时间不能为空");

    // 操作代码
    int code;

    // 提示信息
    String message;

    ArticleCode(int code, String message){
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
        return String.format(" ArticleCode:{code=%s, msg=%s} ", code, message);
    }
}
