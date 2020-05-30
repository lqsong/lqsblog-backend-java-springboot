package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.LinkSearchDTO;
import cc.liqingsong.database.entity.Link;
import cc.liqingsong.database.entity.LinkCategory;
import cc.liqingsong.database.vo.admin.LinkVO;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.service.admin.LinkCategoryService;
import cc.liqingsong.service.admin.LinkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 左邻右舍控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class LinkController extends BaseController {

    private LinkCategoryService linkCategoryService;
    @Autowired
    public void setLinkCategoryService(LinkCategoryService linkCategoryService) {
        this.linkCategoryService = linkCategoryService;
    }

    private LinkService linkService;
    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    /**
     * 左邻右舍列表
     */
    @RequiresPermissions(value = "/admin/v1/links:list")
    @RequestMapping(value = "/links", method = RequestMethod.GET)
    public ResultToken linkList(LinkSearchDTO linkSearchDTO) {
        IPage page = linkService.listVOPage(getPage(), linkSearchDTO);
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }

    /**
     * 左邻右舍添加
     */
    @RequiresPermissions(value = "/admin/v1/links:create")
    @RequestMapping(value = "/links", method = RequestMethod.POST)
    public ResultToken linkCreate(@RequestBody Link link) {
        link.setCreatorId(this.userId);
        boolean save = linkService.save(link);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(link.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 左邻右舍编辑
     */
    @RequiresPermissions(value = "/admin/v1/links:update")
    @RequestMapping(value = "/links/{id}", method = RequestMethod.PUT)
    public ResultToken linkUpdate(@PathVariable Long id, @RequestBody Link link) {
        link.setId(id);
        boolean b = linkService.updateById(link);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 左邻右舍删除
     */
    @RequiresPermissions(value = "/admin/v1/links:delete")
    @RequestMapping(value = "/links/{id}", method = RequestMethod.DELETE)
    public ResultToken linkDelete(@PathVariable Long id) {
        boolean b = linkService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 左邻右舍详情
     */
    @RequiresPermissions(value = "/admin/v1/links:read")
    @RequestMapping(value = "/links/{id}", method = RequestMethod.GET)
    public ResultToken linkRead(@PathVariable Long id) {
        LinkVO link = linkService.getLinkVOById(id);
        return new ResultToken(ResultCode.SUCCESS, link, this.newToken);
    }

    /**
     * 左邻右舍分类列表
     */
    @RequiresPermissions(value = "/admin/v1/links/categorys:list")
    @RequestMapping(value = "/link/categorys", method = RequestMethod.GET)
    public ResultToken categoryList() {
        List<LinkCategory> list = linkCategoryService.list(1, 1);
        return new ResultToken(ResultCode.SUCCESS, list, this.newToken);
    }

    /**
     * 左邻右舍分类添加
     */
    @RequiresPermissions(value = "/admin/v1/links/categorys:create")
    @RequestMapping(value = "/link/categorys", method = RequestMethod.POST)
    public ResultToken categoryCreate(@RequestBody LinkCategory linkCategory) {
        boolean save = linkCategoryService.save(linkCategory);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(linkCategory.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 左邻右舍分类编辑
     */
    @RequiresPermissions(value = "/admin/v1/links/categorys:update")
    @RequestMapping(value = "/link/categorys/{id}", method = RequestMethod.PUT)
    public ResultToken categoryUpdate(@PathVariable Long id, @RequestBody LinkCategory linkCategory) {
        linkCategory.setId(id);
        boolean b = linkCategoryService.updateById(linkCategory);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 左邻右舍分类删除
     */
    @RequiresPermissions(value = "/admin/v1/links/categorys:delete")
    @RequestMapping(value = "/link/categorys/{id}", method = RequestMethod.DELETE)
    public ResultToken categoryDelete(@PathVariable Long id){
        boolean b = linkCategoryService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }


}
