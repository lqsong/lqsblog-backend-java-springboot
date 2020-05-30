package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.Search;
import cc.liqingsong.database.vo.pc.SearchRecommendVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Search Mapper
 * @author liqingsong
 */
public interface SearchMapper  extends BaseMapper<Search> {

    /**
     * 返回推荐列表
     *
     * @param num  读取条数
     * @return
     */
    List<SearchRecommendVO> selectSearchRecommendVO(@Param("num") Long num);
}
