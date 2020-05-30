package cc.liqingsong.api.pc.controller.v1;

import cc.liqingsong.api.pc.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.pc.WorksSearchDTO;
import cc.liqingsong.database.vo.pc.WorksDetailVO;
import cc.liqingsong.service.pc.WorksService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作品控制器
 * @author liqingsong
 */
@RestController("pcV1WorksController")
@RequestMapping("/pc/v1")
public class WorksController extends BaseController {

    private WorksService worksService;
    @Autowired
    public void setWorksService(WorksService worksService) {
        this.worksService = worksService;
    }

    /**
     * 作品列表
     */
    @RequestMapping(value = "/works/list", method = RequestMethod.GET)
    public Result worksList(WorksSearchDTO worksSearchDTO) {
        IPage page = worksService.WorksListVOPage(getPage(), worksSearchDTO);
        return new Result(ResultCode.SUCCESS,new PageData<>(page));
    }

    /**
     * 作品详情
     */
    @RequestMapping(value = "/works/detail", method = RequestMethod.GET)
    public Result worksDetail(Integer id) {
        WorksDetailVO vo = worksService.WorksDetailVOByIdAndAddHit(id);
        if(null == vo) {
            return new Result(ResultCode.NOT_FOUND);
        }
        return new Result(ResultCode.SUCCESS, vo);
    }



}
