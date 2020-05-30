package cc.liqingsong.api.pc.controller.v1;

import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.pc.SearchSearchDTO;
import cc.liqingsong.database.vo.pc.SearchRecommendVO;
import cc.liqingsong.service.pc.SearchService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页控制器
 * @author liqingsong
 */
@RestController("pcV1IndexController")
@RequestMapping("/pc/v1")
public class IndexController extends BaseController {

    private SearchService searchService;
    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }


    /**
     * 首页推荐
     */
    @RequestMapping(value = "/index/recommend", method = RequestMethod.GET)
    public Result indexRecommend() {
        List<SearchRecommendVO> vo = searchService.getSearchRecommendVO(5L);
        return new Result(ResultCode.SUCCESS,vo);
    }


    /**
     * 首页列表
     */
    @RequestMapping(value = "/index/list", method = RequestMethod.GET)
    public Result indexList(String noSid) {
        SearchSearchDTO searchSearchDTO = new SearchSearchDTO();

        if(null != noSid && !noSid.isEmpty()) {
            String[] split = noSid.split(",");
            ArrayList<Long> sid = new ArrayList<>();
            for (String item : split) {
                sid.add(Long.valueOf(item));
            }
            searchSearchDTO.setNoSid(sid);
        }

        IPage page = searchService.listVOPage(getPage(),searchSearchDTO);

        return new Result(ResultCode.SUCCESS,new PageData<>(page));
    }


}
