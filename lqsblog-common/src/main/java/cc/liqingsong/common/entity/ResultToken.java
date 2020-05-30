package cc.liqingsong.common.entity;

import cc.liqingsong.common.enums.IErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据响应对象
 *    {
 *      code    ：返回码
 *      msg ：返回信息
 *      //返回数据
 *      data：{
 *
 *      },
 *      token: 返回新token
 *    }
 * @author liqingsong
 */

@Data
@NoArgsConstructor
public class ResultToken {
    private Integer code; // 返回码
    private String msg; //返回信息
    private Object data; // 返回数据
    private String token; // 返回token


    public ResultToken(IErrorCode code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }

    public ResultToken(IErrorCode code, String token) {
        this.code = code.getCode();
        this.msg = code.getMessage();
        this.token = token;
    }

    public ResultToken(IErrorCode code, Object data) {

        this.code = code.getCode();
        this.msg = code.getMessage();
        this.data = data;
    }

    public ResultToken(IErrorCode code, Object data, String token) {
        this.code = code.getCode();
        this.msg = code.getMessage();
        this.data = data;
        this.token = token;
    }

    public ResultToken(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public ResultToken(Integer code, String message, Object data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public ResultToken(Integer code, String message, Object data, String token) {
        this.code = code;
        this.msg = message;
        this.data = data;
        this.token = token;
    }

    public ResultToken(Integer code, String message, String token) {
        this.code = code;
        this.msg = message;
        this.token = token;
    }

}
