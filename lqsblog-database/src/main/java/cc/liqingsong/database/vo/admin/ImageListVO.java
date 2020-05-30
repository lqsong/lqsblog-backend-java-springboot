package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图片列表 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ImageListVO {

    /** 主键ID */
    private Long id;

    /** 图片地址 */
    private String imgurl;

    /** 图片大小KB */
    private String size;

}
