package cc.liqingsong.service.admin;

import cc.liqingsong.database.dto.admin.SearchSearchDTO;
import cc.liqingsong.database.entity.Article;
import cc.liqingsong.database.entity.Search;
import cc.liqingsong.database.entity.Works;
import cc.liqingsong.database.vo.admin.SearchListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Search 服务类
 *
 * @author liqingsong
 */
public interface SearchService extends IService<Search> {


    /**
     * Article 转 Search
     *
     * @param entity Article 实体类
     * @return
     */
    Search articleToSearch(Article entity);


    /**
     * Works 转 Search
     *
     * @param entity Works 实体类
     * @return
     */
    Search worksToSearch(Works entity);


    /**
     * 根据实体类删除
     *
     * @param entity Works 实体类
     * @return
     */
    boolean remove(Search entity);


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
    IPage<Search> page(IPage<Search> page,  SearchSearchDTO searchSearchDTO);



}
