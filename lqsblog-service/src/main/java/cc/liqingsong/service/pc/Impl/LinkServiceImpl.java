package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.entity.Link;
import cc.liqingsong.database.entity.LinkCategory;
import cc.liqingsong.database.mapper.LinkMapper;
import cc.liqingsong.database.vo.pc.LinkCategoryVO;
import cc.liqingsong.database.vo.pc.LinkVO;
import cc.liqingsong.service.pc.LinkCategoryService;
import cc.liqingsong.service.pc.LinkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Link 服务实现类
 *
 * @author liqingsong
 */
@Service("pcLinkServiceImpl")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    private LinkCategoryService linkCategoryService;
    @Autowired
    public void setLinkCategoryService(LinkCategoryService linkCategoryService){
        this.linkCategoryService = linkCategoryService;
    }


    @Override
    public List<LinkCategoryVO> selectLinkCategoryVOAll() {

        QueryWrapper<LinkCategory> qw = new QueryWrapper<>();
        qw.orderByAsc("sort");
        List<LinkCategory> cList = linkCategoryService.list(qw);

        List<Link> list = super.list();

        HashMap<Long, List<LinkVO>> listMap = new HashMap<>();
        for (Link lItem: list ) {
            LinkVO linkVO = new LinkVO();
            linkVO.setId(lItem.getId());
            linkVO.setTitle(lItem.getTitle());
            linkVO.setDescription(lItem.getDescription());
            linkVO.setHref(lItem.getHref());
            linkVO.setLogo(lItem.getLogo());

            List<LinkVO> linkVOS = listMap.get(lItem.getCategoryId());
            if (null == linkVOS) {
                linkVOS = new ArrayList<>();
            }

            linkVOS.add(linkVO);

            listMap.put(lItem.getCategoryId(),linkVOS);
        }

        List<LinkCategoryVO> linkCategoryVOS = new ArrayList<>();
        for (LinkCategory item: cList ) {
            LinkCategoryVO linkCategoryVO = new LinkCategoryVO();
            linkCategoryVO.setName(item.getName());
            linkCategoryVO.setChildren(listMap.get(item.getId()));
            linkCategoryVOS.add(linkCategoryVO);
        }

        return linkCategoryVOS;
    }

    @Override
    public List<LinkVO> getByCategoryId(List id) {
        Assert.fail(null == id, ResultCode.PID_REQUIRED);
        return baseMapper.getPcLinkVOByCategoryId(id);
    }
}
