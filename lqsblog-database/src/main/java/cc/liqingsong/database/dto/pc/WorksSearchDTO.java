package cc.liqingsong.database.dto.pc;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 作品搜索 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class WorksSearchDTO {

    /** 排序字段[addtime,id,hit] */
    private Integer sort;

    /** [desc 降序，asc 升序] */
    private Integer order;


}
