package cc.liqingsong.api.admin.controller.v1;

import cc.liqingsong.api.admin.controller.BaseController;
import cc.liqingsong.common.entity.PageData;
import cc.liqingsong.common.entity.ResultToken;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.vo.admin.ImageVO;
import cc.liqingsong.service.admin.AttachmentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 上传控制器
 * @author liqingsong
 */
@RestController
@RequestMapping("/admin/v1")
public class UploadController extends BaseController {

    private AttachmentService attachmentService;
    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }


    /**
     * 图片列表
     */
    @RequestMapping(value = "/upload/images", method = RequestMethod.GET)
    public ResultToken imagesList() {
        IPage page = attachmentService.ImageListVOPage(getPage());
        return new ResultToken(ResultCode.SUCCESS, new PageData<>(page), this.newToken);
    }

    /**
     * 图片上传
     */
    @RequestMapping(value = "/upload/images", method = RequestMethod.POST)
    public ResultToken imagesCreate(@RequestParam("file") MultipartFile file) {
        ImageVO vo = attachmentService.save(file, this.userId);
        return new ResultToken(ResultCode.SUCCESS, vo, this.newToken);
    }

    /**
     * 图片下载
     */
    /*
    @RequestMapping(value = "/upload/images/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource> imagesDown(@PathVariable Long id, HttpServletRequest request) {
        return upload.downloadFile("20200517", "9e239d39-d9f3-410d-86db-40e4eaa963a6.png",request);
    }
    */



}
