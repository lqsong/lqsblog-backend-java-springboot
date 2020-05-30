package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.WorksSearchDTO;
import cc.liqingsong.database.entity.Works;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.service.admin.WorksService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 作品控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class WorksController extends BaseController {

    private WorksService worksService;
    @Autowired
    public void setWorksService(WorksService worksService) {
        this.worksService = worksService;
    }

    /**
     * 作品列表
     */
    @RequiresPermissions(value = "/admin/v1/works:list")
    @RequestMapping(value = "/works", method = RequestMethod.GET)
    public ResultToken worksList(WorksSearchDTO worksSearchDTO) {
        IPage page = worksService.page(getPage(), worksSearchDTO);
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }

    /**
     * 作品添加
     */
    @RequiresPermissions(value = "/admin/v1/works:create")
    @RequestMapping(value = "/works", method = RequestMethod.POST)
    public ResultToken worksCreate(@RequestBody Works works) {
        works.setCreatorId(this.userId);
        boolean save = worksService.save(works);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(works.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 作品编辑
     */
    @RequiresPermissions(value = "/admin/v1/works:update")
    @RequestMapping(value = "/works/{id}", method = RequestMethod.PUT)
    public ResultToken worksUpdate(@PathVariable Long id, @RequestBody Works works){
        works.setId(id);
        boolean b = worksService.updateById(works);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 作品删除
     */
    @RequiresPermissions(value = "/admin/v1/works:delete")
    @RequestMapping(value = "/works/{id}", method = RequestMethod.DELETE)
    public ResultToken worksDelete(@PathVariable Long id) {
        boolean b = worksService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 作品详情
     */
    @RequiresPermissions(value = "/admin/v1/works:read")
    @RequestMapping(value = "/works/{id}", method = RequestMethod.GET)
    public ResultToken worksRead(@PathVariable Long id) {
        Works works = worksService.getById(id);
        return new ResultToken(ResultCode.SUCCESS, works, this.newToken);
    }
}
