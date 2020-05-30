package cc.liqingsong.service.pc;

import cc.liqingsong.database.dto.pc.SearchSearchDTO;
import cc.liqingsong.database.entity.Search;
import cc.liqingsong.database.vo.pc.SearchListVO;
import cc.liqingsong.database.vo.pc.SearchRecommendVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Search 服务类
 *
 * @author liqingsong
 */
public interface SearchService extends IService<Search> {

    /**
     * 获取推荐列表
     *
     * @param num  读取的条数
     * @return
     */
    List<SearchRecommendVO> getSearchRecommendVO(Long num);


    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param searchSearchDTO  搜索信息
     * @return
     */
    IPage<SearchListVO> listVOPage(IPage<Search> page, SearchSearchDTO searchSearchDTO);


    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param searchSearchDTO  搜索信息
     * @return
     */
    IPage<Search> page(IPage<Search> page, SearchSearchDTO searchSearchDTO);
}
