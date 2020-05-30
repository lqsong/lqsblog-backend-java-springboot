package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.TopicsSearchDTO;
import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.vo.admin.SaveIdVO;
import cc.liqingsong.service.admin.TopicsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 专题控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class TopicsController extends BaseController {

    private TopicsService topicsService;
    @Autowired
    public  void setTopicsService(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    /**
     * 专题列表
     */
    @RequiresPermissions(value = "/admin/v1/topics:list")
    @RequestMapping(value = "/topics", method = RequestMethod.GET)
    public ResultToken topicsList(TopicsSearchDTO topicsSearchDTO){
        IPage page = topicsService.page(getPage(), topicsSearchDTO);
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }

    /**
     * 专题添加
     */
    @RequiresPermissions(value = "/admin/v1/topics:create")
    @RequestMapping(value = "/topics", method = RequestMethod.POST)
    public ResultToken topicsCreate(@RequestBody Topics topics) {
        topics.setCreatorId(this.userId);
        boolean save = topicsService.save(topics);
        return save ? new ResultToken(ResultCode.SUCCESS, new SaveIdVO().setId(topics.getId()),this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 专题编辑
     */
    @RequiresPermissions(value = "/admin/v1/topics:update")
    @RequestMapping(value = "/topics/{id}", method = RequestMethod.PUT)
    public ResultToken topicsUpdate(@PathVariable Long id, @RequestBody Topics topics) {
        topics.setId(id);
        boolean b = topicsService.updateById(topics);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 专题删除
     */
    @RequiresPermissions(value = "/admin/v1/topics:delete")
    @RequestMapping(value = "/topics/{id}", method = RequestMethod.DELETE)
    public ResultToken topicsDelete(@PathVariable Long id) {
        boolean b = topicsService.removeById(id);
        return b ? new ResultToken(ResultCode.SUCCESS,this.newToken) : new ResultToken(ResultCode.FAIL,this.newToken);
    }

    /**
     * 专题详情
     */
    @RequiresPermissions(value = "/admin/v1/topics:read")
    @RequestMapping(value = "/topics/{id}", method = RequestMethod.GET)
    public ResultToken topicsRead(@PathVariable Long id) {
        Topics topics = topicsService.getById(id);
        return new ResultToken(ResultCode.SUCCESS, topics, this.newToken);
    }
}
