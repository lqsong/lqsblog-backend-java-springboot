package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.TagSearchDTO;
import cc.liqingsong.database.entity.Tag;
import cc.liqingsong.database.enums.TagCode;
import cc.liqingsong.database.mapper.TagMapper;
import cc.liqingsong.database.vo.admin.TagVO;
import cc.liqingsong.service.admin.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Tag 服务实现类
 *
 * @author liqingsong
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * 排序字段
     */
    private String[] sort = new String[]{"id","hit"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }

    /**
     * 插入一条记录
     *
     * @param tag 实体类对象
     */
    @Override
    public boolean save(Tag tag) {
        if (null == tag) {
            return false;
        }

        saveUpdateVerify(tag, "save");

        return super.save(tag);
    }

    /**
     * 根据 ID 修改
     *
     * @param tag 实体类对象
     */
    @Override
    public boolean updateById(Tag tag) {

        if (null == tag) {
            return false;
        }

        Assert.fail(null == tag.getId(), ResultCode.ID_NOT_FOUND);

        saveUpdateVerify(tag, "update");

        return super.updateById(tag);
    }

    @Override
    public IPage<Tag> page(IPage<Tag> page, TagSearchDTO tagSearchDTO) {
        QueryWrapper<Tag> qw = new QueryWrapper<>();
        if (null != tagSearchDTO.getKeywords()) {
            qw.like("name",tagSearchDTO.getKeywords()).or().like("pinyin",tagSearchDTO.getKeywords());
        }

        String sort = tagSearchDTO.getSort() == null ? getSort(0) : getSort(tagSearchDTO.getSort());
        if (null != tagSearchDTO.getOrder() && tagSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }
        return super.page(page, qw);
    }

    @Override
    public List<TagVO> searchKeywordsLimitVO(String keywords) {

        if (null == keywords || "" == keywords) {
            return new ArrayList<>();
        }

        return baseMapper.selectKeywordsLimitVO(keywords);
    }

    /**
     * 私用验证
     *
     * @param tag 实体类对象
     * @param type 类型  save、update
     */
    private void saveUpdateVerify(Tag tag, String type) {
        Assert.fail(null == tag.getName(), ResultCode.NAME_NOT_EMPTY);
        int nameLen = tag.getName().length();
        Assert.fail(nameLen > 10 || nameLen < 1, TagCode.NAME_LENGTH_WORDS);

        Assert.fail(null == tag.getPinyin(), TagCode.PINYIN_NOT_EMPTY);
        int pingyinLen = tag.getPinyin().length();
        Assert.fail(pingyinLen > 10 || pingyinLen < 1, TagCode.PINYIN_LENGTH_WORDS);

        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",tag.getName());

        switch(type){
            case "update" :
                queryWrapper.ne("id", tag.getId());
                break;
            default :
        }

        int count = super.count(queryWrapper);
        Assert.fail( count > 0, ResultCode.NAME_THE_SAME);

    }


}
