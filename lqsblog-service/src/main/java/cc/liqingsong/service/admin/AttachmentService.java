package cc.liqingsong.service.admin;

import cc.liqingsong.database.entity.Attachment;
import cc.liqingsong.database.vo.admin.ImageListVO;
import cc.liqingsong.database.vo.admin.ImageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * Attachment 服务类
 *
 * @author liqingsong
 */
public interface AttachmentService extends IService<Attachment> {

    /**
     * 插入一条记录(上传图片)
     *
     * @param file MultipartFile对象
     * @param userId 当前登录的用户id
     */
    ImageVO save(MultipartFile file, Long userId);

    /**
     * 图片列表带分页
     *
     * @param page 分页对象
     * @return
     */
    IPage<ImageListVO> ImageListVOPage(IPage<Attachment> page);
}
