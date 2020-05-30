package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.Article;
import cc.liqingsong.database.vo.admin.ArticleSimplifyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Article Mapper
 * @author liqingsong
 */
public interface ArticleMapper extends BaseMapper<Article> {


    /**
     * ids搜索 返回简化列表
     *
     * @param ids  ID
     * @return
     */
    List<ArticleSimplifyVO> selectSimplifyVO(@Param("ids") List<Object> ids);


}
