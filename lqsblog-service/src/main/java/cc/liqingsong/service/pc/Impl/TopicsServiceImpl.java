package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.dto.pc.TopicsSearchDTO;
import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.mapper.TopicsMapper;
import cc.liqingsong.database.vo.pc.TopicsDetailVO;
import cc.liqingsong.database.vo.pc.TopicsListVO;
import cc.liqingsong.service.pc.TopicsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Topics 服务实现类
 *
 * @author liqingsong
 */
@Service("pcTopicsServiceImpl")
public class TopicsServiceImpl extends ServiceImpl<TopicsMapper, Topics> implements TopicsService {

    /**
     * 排序字段
     */
    private String[] sort = new String[]{"id","hit"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }


    @Override
    public IPage<TopicsListVO> TopicsListVOPage(IPage<Topics> page, TopicsSearchDTO topicsSearchDTO) {
        // 读取分页数据
        IPage<Topics> list = page(page, topicsSearchDTO);

        // 设置返回数据
        Page<TopicsListVO> topicsListVOPage = new Page<>();
        topicsListVOPage.setCurrent(list.getCurrent());
        topicsListVOPage.setTotal(list.getTotal());
        topicsListVOPage.setPages(list.getPages());
        topicsListVOPage.setSize(list.getSize());
        topicsListVOPage.setOrders(list.orders());
        topicsListVOPage.setOptimizeCountSql(list.optimizeCountSql());
        topicsListVOPage.setSearchCount(list.isSearchCount());

        if(list.getRecords().isEmpty()) {
            topicsListVOPage.setRecords(new ArrayList<>());
            return topicsListVOPage;
        }


        // 设置数据
        List<TopicsListVO> listVo = new ArrayList<>();
        for (Topics item: list.getRecords() ) {
            TopicsListVO topicsListVO = new TopicsListVO();
            topicsListVO.setId(item.getId());
            topicsListVO.setTitle(item.getTitle());
            topicsListVO.setAlias(item.getAlias());

            JSONArray jsonArray = JSON.parseArray(item.getContentStr());
            int size = jsonArray.size();
            topicsListVO.setQuantity((long) size);

            if(size > 3) {
                List<Object> newjsonArr = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    newjsonArr.add(jsonArray.get(i));
                }
                topicsListVO.setConlist(newjsonArr);
            } else {
                topicsListVO.setConlist(jsonArray);
            }

            listVo.add(topicsListVO);
        }

        topicsListVOPage.setRecords(listVo);
        return topicsListVOPage;
    }

    @Override
    public IPage<Topics> page(IPage<Topics> page, TopicsSearchDTO topicsSearchDTO) {
        QueryWrapper<Topics> qw = new QueryWrapper<>();

        String sort = topicsSearchDTO.getSort() == null ? getSort(0) : getSort(topicsSearchDTO.getSort());
        if (null != topicsSearchDTO.getOrder() && topicsSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }
        return super.page(page, qw);
    }

    @Override
    public TopicsDetailVO TopicsDetailVOByAlias(String alias) {
        QueryWrapper<Topics> qw = new QueryWrapper<>();
        qw.eq("alias",alias);
        Topics info = super.getOne(qw, false);
        if(null == info) {
            return null;
        }

        TopicsDetailVO topicsDetailVO = new TopicsDetailVO();
        topicsDetailVO.setId(info.getId());
        topicsDetailVO.setTitle(info.getTitle());
        topicsDetailVO.setKeywords(info.getKeywords());
        topicsDetailVO.setDescription(info.getDescription());
        topicsDetailVO.setList(JSON.parseArray(info.getContentStr()));
        topicsDetailVO.setHit(info.getHit());
        topicsDetailVO.setAddtime(info.getAddtime());

        return topicsDetailVO;
    }

    @Override
    public TopicsDetailVO TopicsDetailVOByAliasAndAddHit(String alias) {
        TopicsDetailVO info = TopicsDetailVOByAlias(alias);
        if( null == info) {
            return null;
        }

        Topics topics = new Topics();
        topics.setId(info.getId());
        // 浏览量 +1
        topics.setHit(info.getHit() + 1);
        this.updateById(topics);

        return info;
    }
}
