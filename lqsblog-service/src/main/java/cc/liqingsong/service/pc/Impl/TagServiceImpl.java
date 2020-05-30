package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.Tag;
import cc.liqingsong.database.mapper.TagMapper;
import cc.liqingsong.service.pc.TagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Tag 服务实现类
 *
 * @author liqingsong
 */
@Service("pcTagServiceImpl")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public Tag getByName(String name) {
        QueryWrapper<Tag> qw = new QueryWrapper<>();
        qw.eq("name", name);
        return super.getOne(qw, false);
    }

    @Override
    public Tag getByNameAndAddHit(String name) {
        Tag info = getByName(name);
        if(null == info) {
            return null;
        }

        Tag tag = new Tag();
        tag.setId(info.getId());
        // 浏览量 +1
        tag.setHit(info.getHit() + 1);
        this.updateById(tag);

        return info;
    }

}
