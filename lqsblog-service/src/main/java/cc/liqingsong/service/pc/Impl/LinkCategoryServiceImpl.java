package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.entity.LinkCategory;
import cc.liqingsong.database.mapper.LinkCategoryMapper;
import cc.liqingsong.service.pc.LinkCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * LinkCategory 服务实现类
 *
 * @author liqingsong
 */
@Service("pcLinkCategoryServiceImpl")
public class LinkCategoryServiceImpl  extends ServiceImpl<LinkCategoryMapper, LinkCategory> implements LinkCategoryService {
}
