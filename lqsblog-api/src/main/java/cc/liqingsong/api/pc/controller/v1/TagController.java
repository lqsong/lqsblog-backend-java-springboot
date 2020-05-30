package cc.liqingsong.api.pc.controller.v1;

import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.common.utils.IpUtil;
import cc.liqingsong.database.dto.pc.SearchSearchDTO;
import cc.liqingsong.database.entity.Tag;
import cc.liqingsong.service.pc.SearchService;
import cc.liqingsong.service.pc.TagLogService;
import cc.liqingsong.service.pc.TagService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


/**
 * 标签控制器
 * @author liqingsong
 */
@RestController("pcV1TagController")
@RequestMapping("/pc/v1")
public class TagController  extends BaseController {

    private SearchService searchService;
    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    private TagService tagService;
    @Autowired
    public void setTagService(TagService tagService){
        this.tagService = tagService;
    }

    private TagLogService tagLogService;
    @Autowired
    public void setTagLogService(TagLogService tagLogService){
        this.tagLogService = tagLogService;
    }


    /**
     * 标签下内容列表
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.GET)
    public Result tagList(String name) {
        SearchSearchDTO searchSearchDTO = new SearchSearchDTO();
        if(null != name && !name.isEmpty() && !name.equals("")) {
            searchSearchDTO.setTag(name);
        } else {
            return new Result(ResultCode.SUCCESS,new ArrayList<>());
        }

        // 添加Log
        String ipAddr = IpUtil.getIpAddr(this.request);
        tagLogService.saveTagIp(name,ipAddr);

        IPage page = searchService.listVOPage(getPage(),searchSearchDTO);

        return new Result(ResultCode.SUCCESS,new PageData<>(page));
    }

    /**
     * 标签详情
     */
    @RequestMapping(value = "/tag/detail", method = RequestMethod.GET)
    public Result tagDetail(String name) {
        Tag vo = tagService.getByNameAndAddHit(name);
        if(null == vo) {
            return new Result(ResultCode.NOT_FOUND);
        }
        return new Result(ResultCode.SUCCESS,vo);
    }


}
