package cc.liqingsong.database.vo.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图片 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ImageVO {

    /** 文件名 */
    private String title;

    /** 文件网址 */
    private String url;
}
