package cc.liqingsong.database.vo.pc;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 左邻右舍 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class LinkCategoryVO {

    /** 分类名称 */
    private String name;

    /** 链接 */
    private List<LinkVO> children;


}
