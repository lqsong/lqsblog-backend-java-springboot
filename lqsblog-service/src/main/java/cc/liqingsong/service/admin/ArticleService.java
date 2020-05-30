package cc.liqingsong.service.admin;

import cc.liqingsong.database.dto.admin.ArticleSearchDTO;
import cc.liqingsong.database.entity.Article;
import cc.liqingsong.database.vo.admin.ArticleDailyNewVO;
import cc.liqingsong.database.vo.admin.ArticleInterestVO;
import cc.liqingsong.database.vo.admin.ArticleListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Article 服务类
 *
 * @author liqingsong
 */
public interface ArticleService extends IService<Article> {

    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param articleSearchDTO  搜索信息
     * @return
     */
    IPage<ArticleListVO> listVOPage(IPage<Article> page, ArticleSearchDTO articleSearchDTO);


    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param articleSearchDTO  搜索信息
     * @return
     */
    IPage<Article> page(IPage<Article> page, ArticleSearchDTO articleSearchDTO);


    /**
     * 根据id 查询 定义格式的文章详情
     *
     * @param id ID
     * @return
     */
    ArticleInterestVO getArticleInterestVOById(Long id);

    /**
     * 统计 - 日新增，总量，日同比，周同比
     *
     * @return
     */
    ArticleDailyNewVO getArticleDailyNewVO();

}
