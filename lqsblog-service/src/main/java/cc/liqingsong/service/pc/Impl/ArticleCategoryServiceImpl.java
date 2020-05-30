package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.entity.SinglePage;
import cc.liqingsong.database.mapper.ArticleCategoryMapper;
import cc.liqingsong.service.pc.ArticleCategoryService;
import cc.liqingsong.service.pc.SinglePageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ArticleCategory 服务实现类
 *
 * @author liqingsong
 */
@Service("pcArticleCategoryServiceImpl")
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

    private SinglePageService singlePageService;
    @Autowired
    public void setSinglePageService(SinglePageService singlePageService){
        this.singlePageService = singlePageService;
    }

    @Override
    public ArticleCategory selectByAlias(String alias) {
        QueryWrapper<ArticleCategory> qw = new QueryWrapper<>();
        qw.eq("alias",alias);
        return super.getOne(qw, false);
    }

    @Override
    public ArticleCategory selectByAliasAndAddHit(String alias) {

        // 如果为空 读取 默认配置的父分类说明
        if(null == alias || alias.equals("") || alias.isEmpty()) {
            SinglePage spInfo = singlePageService.getByIdAndAddHit(2);
            if(null == spInfo) {
                return null;
            }
            ArticleCategory category = new ArticleCategory();
            category.setName(spInfo.getName());
            category.setTitle(spInfo.getTitle());
            category.setKeywords(spInfo.getKeywords());
            category.setDescription(spInfo.getDescription());
            return category;
        }


        ArticleCategory articleCategory = selectByAlias(alias);
        if (null == articleCategory) {
            return null;
        }


        ArticleCategory articleCategory1 = new ArticleCategory();
        articleCategory1.setId(articleCategory.getId());
        // 浏览量 + 1
        articleCategory1.setHit(articleCategory.getHit() + 1);

        this.updateById(articleCategory1);

        return articleCategory;
    }
}
