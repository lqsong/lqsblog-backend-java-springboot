package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.Tag;
import cc.liqingsong.database.vo.admin.TagVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Tag Mapper
 * @author liqingsong
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据keywords搜索列表 显示前10条
     *
     * @param keywords 关键词
     * @return
     */
    List<TagVO> selectKeywordsLimitVO(@Param("keywords") String keywords);



}
