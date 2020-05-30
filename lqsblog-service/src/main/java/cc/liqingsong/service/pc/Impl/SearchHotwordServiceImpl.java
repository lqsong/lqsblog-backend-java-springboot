package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.SearchHotword;
import cc.liqingsong.database.mapper.SearchHotwordMapper;
import cc.liqingsong.service.pc.SearchHotwordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * SearchHotword 服务实现类
 *
 * @author liqingsong
 */
@Service("pcSearchHotwordServiceImpl")
public class SearchHotwordServiceImpl   extends ServiceImpl<SearchHotwordMapper, SearchHotword> implements SearchHotwordService {

    @Override
    public boolean saveHotWord(String hotWord) {
        if(null == hotWord || hotWord.equals("") || hotWord.isEmpty()) {
            return false;
        }
        SearchHotword entity = new SearchHotword();
        SearchHotword info = getByName(hotWord);
        if(null == info) {
            entity.setName(hotWord);
            return super.save(entity);
        } else {
            entity.setId(info.getId());
            entity.setHit(info.getHit() + 1);
            return super.updateById(entity);
        }
    }

    @Override
    public SearchHotword getByName(String name) {
        QueryWrapper<SearchHotword> qw = new QueryWrapper<>();
        qw.eq("name", name);
        return super.getOne(qw, false);
    }
}
