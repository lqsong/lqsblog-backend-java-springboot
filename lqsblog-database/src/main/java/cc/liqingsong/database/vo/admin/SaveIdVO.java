package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 当前添加或修改数据返回的ID VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class SaveIdVO {

    /** id */
    private Long id;

}
