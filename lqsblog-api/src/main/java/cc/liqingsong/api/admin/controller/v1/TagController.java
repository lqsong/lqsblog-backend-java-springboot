package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.TagSearchDTO;
import cc.liqingsong.database.entity.Tag;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.database.vo.admin.TagVO;
import cc.liqingsong.service.admin.TagService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class TagController extends BaseController {

    private TagService tagService;
    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * 标签列表
     */
    @RequiresPermissions(value = "/admin/v1/tags:list")
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public ResultToken tagsList(TagSearchDTO tagSearchDTO) {
        IPage page = tagService.page(getPage(), tagSearchDTO);
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }

    /**
     * 标签添加
     */
    @RequiresPermissions(value = "/admin/v1/tags:create")
    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public ResultToken tagsCreate(@RequestBody Tag tag) {
        boolean save = tagService.save(tag);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(tag.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 标签编辑
     */
    @RequiresPermissions(value = "/admin/v1/tags:update")
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.PUT)
    public ResultToken tagsUpdate(@PathVariable Long id, @RequestBody Tag tag) {
        tag.setId(id);
        boolean b = tagService.updateById(tag);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 标签删除
     */
    @RequiresPermissions(value = "/admin/v1/tags:delete")
    @RequestMapping(value = "/tags/{id}", method = RequestMethod.DELETE)
    public ResultToken tagsDelete(@PathVariable Long id) {
        boolean b = tagService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 标签搜索下拉列表
     */
    // @RequiresPermissions(value = "/admin/v1/tags/search")
    @RequestMapping(value = "/tags/search", method = RequestMethod.GET)
    public ResultToken tagsSearch(@RequestParam String keywords) {
        List<TagVO> list = tagService.searchKeywordsLimitVO(keywords);
        return new ResultToken(ResultCode.SUCCESS, list, this.newToken);
    }

}
