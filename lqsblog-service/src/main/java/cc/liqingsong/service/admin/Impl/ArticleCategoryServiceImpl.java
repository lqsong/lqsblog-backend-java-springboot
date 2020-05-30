package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Article;
import cc.liqingsong.database.entity.ArticleCategory;
import cc.liqingsong.database.enums.ArticleCode;
import cc.liqingsong.database.mapper.ArticleCategoryMapper;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.service.admin.ArticleCategoryService;
import cc.liqingsong.service.admin.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ArticleCategory 服务实现类
 *
 * @author liqingsong
 */
@Service
public class ArticleCategoryServiceImpl  extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

    private ArticleService articleService;
    @Autowired
    @Lazy
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean save(ArticleCategory entity) {
        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getPId(), ResultCode.PID_REQUIRED);

        saveUpdateVerify(entity, "save");

        if(entity.getPId() > 0) {
            ArticleCategory articleCategory = super.getById(entity.getPId());
            Assert.fail( null == articleCategory, ResultCode.PID_NOT_FOUND);
        }

        return super.save(entity);
    }

    /**
     * 删除一条记录根据id
     *
     * @param id ID
     */
    @Override
    public boolean removeById(Serializable id) {

        QueryWrapper<ArticleCategory> qw = new QueryWrapper<>();
        qw.eq("p_id",id);
        int count = super.count(qw);
        Assert.fail( count > 0, ResultCode.UNABLE_TO_DEL_HAVING_CHILD);

        QueryWrapper<Article> aQw = new QueryWrapper<>();
        aQw.eq("category_id", id);
        int aCount = articleService.count(aQw);
        Assert.fail( aCount > 0, ArticleCode.CATEGORY_ID_LINK_DATA);

        return super.removeById(id);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean updateById(ArticleCategory entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        // 针对父id不做修改
        entity.setPId(null);

        saveUpdateVerify(entity, "update");

        return super.updateById(entity);
    }


    /**
     * 私用验证
     *
     * @param entity 实体类对象
     * @param type 类型  save、update
     */
    private void saveUpdateVerify(ArticleCategory entity, String type) {

        Assert.fail(null == entity.getName(), ResultCode.NAME_NOT_EMPTY);
        int nameLen = entity.getName().length();
        Assert.fail(nameLen > 8 || nameLen < 1, ArticleCode.CATEGORY_NAME_LENGTH_WORDS);

        Assert.fail(null == entity.getAlias(), ArticleCode.CATEGORY_ALIAS_NOT_EMPTY);
        int aliasLen = entity.getAlias().length();
        Assert.fail(aliasLen > 10 || aliasLen < 1, ArticleCode.CATEGORY_ALIAS_LENGTH_WORDS);

        Assert.fail(null == entity.getTitle(), ResultCode.INCORRECT_PARAMETER);
        int titleLen = entity.getTitle().length();
        Assert.fail(titleLen > 30, ResultCode.TITLE_LENGTH_WORDS);

        Assert.fail(null == entity.getKeywords(), ResultCode.INCORRECT_PARAMETER);
        int keyLen = entity.getKeywords().length();
        Assert.fail(keyLen > 50, ResultCode.KEYWORDS_LENGTH_WORDS);

        Assert.fail(null == entity.getDescription(), ResultCode.INCORRECT_PARAMETER);
        int desLen = entity.getDescription().length();
        Assert.fail(desLen > 100, ResultCode.DESCRIPTION_LENGTH_WORDS);

        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("alias",entity.getAlias());
        switch(type) {
            case "update" :
                queryWrapper.ne("id", entity.getId());
                break;
            default :
        }
        int count = super.count(queryWrapper);
        Assert.fail( count > 0, ArticleCode.CATEGORY_ALIAS_THE_SAME);

    }


    @Override
    public List<ArticleCategory> listByPid(Long pId) {
        if (null == pId) {
            return new ArrayList<>();
        }
        QueryWrapper<ArticleCategory> qw = new QueryWrapper<>();
        qw.eq("p_id",pId);
        return super.list(qw);
    }

    @Override
    public List<CascaderVO> selectCascaderVO(Long pId) {
        if (null == pId) {
            return new ArrayList<>();
        }
        return baseMapper.selectCascaderVO(pId);
    }
}
