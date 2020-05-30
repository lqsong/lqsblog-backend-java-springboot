package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.SearchSearchDTO;
import cc.liqingsong.service.admin.SearchHotwordService;
import cc.liqingsong.service.admin.SearchService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 搜索控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class SearchController  extends BaseController {

    private SearchService searchService;
    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    private SearchHotwordService searchHotwordService;
    @Autowired
    public void setSearchHotwordService(SearchHotwordService searchHotwordService) {
        this.searchHotwordService = searchHotwordService;
    }

    /**
     * 搜索列表
     */
    @RequestMapping(value = "/searchs", method = RequestMethod.GET)
    public ResultToken searchList(SearchSearchDTO searchSearchDTO) {
        IPage page = searchService.listVOPage(getPage(), searchSearchDTO);
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }

    /**
     * 搜索热门关键词列表
     */
    @RequestMapping(value = "/searchs/keywords", method = RequestMethod.GET)
    public ResultToken keywordsList() {
        IPage page = searchHotwordService.pageOrderHitDesc(getPage());
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }


}
