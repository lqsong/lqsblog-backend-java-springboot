package cc.liqingsong.database.vo.admin;

import cc.liqingsong.database.entity.Article;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 文章 VO
 * @author liqingsong
 */
@Data
@Accessors(chain = true)
public class ArticleInterestVO extends Article {

    /**
     * 推荐文章 集合
     * id, title, addtime
     */
    List<ArticleSimplifyVO> interest;
}
