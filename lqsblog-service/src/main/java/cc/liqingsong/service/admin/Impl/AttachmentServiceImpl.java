package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.common.utils.Upload;
import cc.liqingsong.database.entity.Attachment;
import cc.liqingsong.database.mapper.AttachmentMapper;
import cc.liqingsong.database.vo.admin.ImageListVO;
import cc.liqingsong.database.vo.admin.ImageVO;
import cc.liqingsong.service.admin.AttachmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Attachment 服务实现类
 *
 * @author liqingsong
 */
@Service
public class AttachmentServiceImpl  extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    private Upload upload;
    @Autowired
    public void setUpload(Upload upload) {
        this.upload = upload;
    }


    @Override
    public ImageVO save(MultipartFile file, Long userId) {

        Map<String,String> upfile = upload.createFile(file,upload.getImgType());
        System.out.println(upfile);

        Attachment attachment = new Attachment();
        attachment.setCreatorId(userId);
        attachment.setCreateTime(LocalDateTime.now());
        attachment.setFileOldName(upfile.get("originalFileName"));
        attachment.setFileName(upfile.get("fileName"));
        attachment.setFileSubDir(upfile.get("subDir"));
        attachment.setFileType(upfile.get("type"));
        attachment.setFileSize(Long.parseLong(upfile.get("size")));
        attachment.setFileSuffix(upfile.get("suffix"));

        boolean b = super.save(attachment);
        Assert.fail(false == b, ResultCode.SAVE_DATA_ERR);

        ImageVO vo = new ImageVO();
        vo.setTitle(attachment.getFileName());
        vo.setUrl(upload.getWebUrlStatic() + attachment.getFileSubDir() + "/" + attachment.getFileName() );
        return vo;

    }

    @Override
    public IPage<ImageListVO> ImageListVOPage(IPage<Attachment> page) {

        // 读取分页数据
        QueryWrapper<Attachment> qw = new QueryWrapper<>();
        //String[] strings = {"image/png", "image/jpg", "image/jpeg", "image/gif"};
        qw.in("file_type", upload.getImgType());
        qw.orderByDesc("id");
        IPage<Attachment> list = super.page(page, qw);
        // 设置返回数据
        Page<ImageListVO> imageListVOPage = new Page<>();
        imageListVOPage.setCurrent(list.getCurrent());
        imageListVOPage.setTotal(list.getTotal());
        imageListVOPage.setPages(list.getPages());
        imageListVOPage.setSize(list.getSize());
        imageListVOPage.setOrders(list.orders());
        imageListVOPage.setOptimizeCountSql(list.optimizeCountSql());
        imageListVOPage.setSearchCount(list.isSearchCount());

        if(list.getRecords().isEmpty()) {
            imageListVOPage.setRecords(new ArrayList<>());
            return imageListVOPage;
        }

        // 设置数据
        List<ImageListVO> listVo= new ArrayList<>();
        for (Attachment item2 : list.getRecords() ) {
            ImageListVO aListVo = new ImageListVO();
            aListVo.setId(item2.getId());
            aListVo.setImgurl(upload.getWebUrlStatic() + item2.getFileSubDir()  + "/" + item2.getFileName());
            aListVo.setSize(item2.getFileSize() / 1024 + "KB");
            listVo.add(aListVo);
        }

        imageListVOPage.setRecords(listVo);
        return imageListVOPage;
    }
}
