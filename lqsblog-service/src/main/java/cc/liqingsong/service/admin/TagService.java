package cc.liqingsong.service.admin;

import cc.liqingsong.database.dto.admin.TagSearchDTO;
import cc.liqingsong.database.entity.Tag;
import cc.liqingsong.database.vo.admin.TagVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Tag 服务类
 *
 * @author liqingsong
 */
public interface TagService extends IService<Tag> {

    /**
     * 根据标签信息分页
     *
     * @param page 分页对象
     * @param tagSearchDTO  搜索信息
     * @return
     */
    IPage<Tag> page(IPage<Tag> page, TagSearchDTO tagSearchDTO);

    /**
     * 根据keywords搜索列表 显示前10条
     *
     * @param keywords 关键词
     * @return
     */
    List<TagVO> searchKeywordsLimitVO(String keywords);

}
