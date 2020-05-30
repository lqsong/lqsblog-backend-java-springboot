package cc.liqingsong.service.pc;

import cc.liqingsong.database.entity.ArticleCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * ArticleCategory 服务类
 *
 * @author liqingsong
 */
public interface ArticleCategoryService extends IService<ArticleCategory> {


    /**
     * 根据别名查询信息
     *
     * @param alias 别名
     * @return
     */
    ArticleCategory selectByAlias(String alias);


    /**
     * 根据别名查询信息，并添加浏览量
     *
     * @param alias 别名
     * @return
     */
    ArticleCategory selectByAliasAndAddHit(String alias);

}
