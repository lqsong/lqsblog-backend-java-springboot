package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.database.entity.SearchHotword;
import cc.liqingsong.database.mapper.SearchHotwordMapper;
import cc.liqingsong.service.admin.SearchHotwordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * SearchHotword 服务实现类
 *
 * @author liqingsong
 */
@Service
public class SearchHotwordServiceImpl   extends ServiceImpl<SearchHotwordMapper, SearchHotword> implements SearchHotwordService {


    @Override
    public IPage<SearchHotword> pageOrderHitDesc(IPage<SearchHotword> page) {
        QueryWrapper<SearchHotword> qw = new QueryWrapper<>();
        qw.orderByDesc("hit");
        return super.page(page, qw);
    }
}
