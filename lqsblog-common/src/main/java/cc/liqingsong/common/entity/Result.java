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
 *      data：  ：{
 *
 *      }
 *    }
 * @author liqingsong
 */
@Data
@NoArgsConstructor
public class Result {

    private Integer code; // 返回码
    private String msg; //返回信息
    private Object data; // 返回数据

    public Result(IErrorCode code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }

    public Result(IErrorCode code, Object data) {
        this.code = code.getCode();
        this.msg = code.getMessage();
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }


}
