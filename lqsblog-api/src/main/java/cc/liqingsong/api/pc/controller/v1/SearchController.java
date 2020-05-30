package cc.liqingsong.api.pc.controller.v1;

import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.common.utils.IpUtil;
import cc.liqingsong.database.dto.pc.SearchSearchDTO;
import cc.liqingsong.service.pc.SearchHotwordLogService;
import cc.liqingsong.service.pc.SearchHotwordService;
import cc.liqingsong.service.pc.SearchService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 搜索控制器
 * @author liqingsong
 */
@RestController("pcV1SearchController")
@RequestMapping("/pc/v1")
public class SearchController extends BaseController {

    private SearchService searchService;
    @Autowired
    public void setSearchService(SearchService searchService){
        this.searchService = searchService;
    }

    private SearchHotwordLogService searchHotwordLogService;
    @Autowired
    public void setSearchHotwordLogService(SearchHotwordLogService searchHotwordLogService) {
        this.searchHotwordLogService = searchHotwordLogService;
    }

    private SearchHotwordService searchHotwordService;
    @Autowired
    public void setSearchHotwordService(SearchHotwordService searchHotwordService) {
        this.searchHotwordService = searchHotwordService;
    }


    /**
     * 搜索
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result searchList(SearchSearchDTO searchSearchDTO) {
        // 添加Log
        String ipAddr = IpUtil.getIpAddr(this.request);
        String keywords = searchSearchDTO.getKeywords();
        searchHotwordLogService.saveHotWordIp(keywords,ipAddr);
        searchHotwordService.saveHotWord(keywords);

        IPage page = searchService.listVOPage(getPage(), searchSearchDTO);
        return new Result(ResultCode.SUCCESS,new PageData<>(page));
    }

}
