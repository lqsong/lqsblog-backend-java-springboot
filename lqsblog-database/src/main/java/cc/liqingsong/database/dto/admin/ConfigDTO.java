package cc.liqingsong.database.dto.admin;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 站点配置 DTO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ConfigDTO {

    /** 网站关键词 */
    private String keywords;

    /** 网站简介 */
    private String description;

    /** 站点创建年份 */
    private String siteCreationTime;

    /** 网站备案号 */
    private String icp;

    /** 版权人 */
    private String copyrightPerson;

    /** 版权网址 */
    private String copyrightUrl;

}
