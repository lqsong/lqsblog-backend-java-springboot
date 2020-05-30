package cc.liqingsong.service.pc;

import cc.liqingsong.database.dto.pc.ArticleSearchDTO;
import cc.liqingsong.database.entity.Article;
import cc.liqingsong.database.vo.pc.ArticleDetailVO;
import cc.liqingsong.database.vo.pc.ArticleListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * Article 服务类
 *
 * @author liqingsong
 */
public interface ArticleService  extends IService<Article> {


    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param articleSearchDTO  搜索信息
     * @return
     */
    IPage<ArticleListVO> ArticleListVOPage(IPage<Article> page, ArticleSearchDTO articleSearchDTO);


    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param articleSearchDTO  搜索信息
     * @return
     */
    IPage<Article> page(IPage<Article> page, ArticleSearchDTO articleSearchDTO);


    /**
     * 根据id查找详情
     *
     * @param id ID
     * @return
     */
    ArticleDetailVO ArticleDetailVOById(Serializable id);


    /**
     * 根据id查找详情 ，并添加浏览量
     *
     * @param id ID
     * @return
     */
    ArticleDetailVO ArticleDetailVOByIdAndAddHit(Serializable id);


    /**
     * 根据ids查找列表
     *
     * @param ids  id ，链接
     * @return
     */
    List<ArticleListVO> ArticleListVOByIds(String ids);


}
