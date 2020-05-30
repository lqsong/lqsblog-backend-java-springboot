package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.vo.admin.CascaderVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ArticleCategory Mapper
 * @author liqingsong
 */
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    /**
     * 根据pid搜索 返回联动列表
     *
     * @param pid 父id
     * @return
     */
    List<CascaderVO> selectCascaderVO(@Param("pid") Long pid);

}
