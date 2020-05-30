package cc.liqingsong.service.pc.Impl;

import cc.liqingsong.database.dto.pc.WorksSearchDTO;
import cc.liqingsong.database.entity.Works;
import cc.liqingsong.database.mapper.WorksMapper;
import cc.liqingsong.database.vo.pc.WorksDetailVO;
import cc.liqingsong.database.vo.pc.WorksListVO;
import cc.liqingsong.service.pc.WorksService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Works 服务实现类
 *
 * @author liqingsong
 */
@Service("pcWorksServiceImpl")
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements WorksService {


    /**
     * 排序字段
     */
    private String[] sort = new String[]{"addtime","id","hit"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }


    @Override
    public IPage<WorksListVO> WorksListVOPage(IPage<Works> page, WorksSearchDTO worksSearchDTO) {

        // 读取分页数据
        IPage<Works> list = page(page, worksSearchDTO);

        Page<WorksListVO> worksListVOPage = new Page<>();
        worksListVOPage.setCurrent(list.getCurrent());
        worksListVOPage.setTotal(list.getTotal());
        worksListVOPage.setPages(list.getPages());
        worksListVOPage.setSize(list.getSize());
        worksListVOPage.setOrders(list.orders());
        worksListVOPage.setOptimizeCountSql(list.optimizeCountSql());
        worksListVOPage.setSearchCount(list.isSearchCount());

        if(list.getRecords().isEmpty()) {
            worksListVOPage.setRecords(new ArrayList<>());
            return worksListVOPage;
        }

        // 设置数据
        ArrayList<WorksListVO> listVo = new ArrayList<>();
        for (Works item: list.getRecords()) {
            WorksListVO worksListVO = new WorksListVO();
            worksListVO.setId(item.getId());
            worksListVO.setTitle(item.getTitle());
            worksListVO.setDescription(item.getDescription());
            worksListVO.setThumb(Arrays.asList(item.getThumb().split("\\|")));
            worksListVO.setAddtime(item.getAddtime());
            worksListVO.setTag(item.getTag());
            worksListVO.setHit(item.getHit());
            listVo.add(worksListVO);
        }

        worksListVOPage.setRecords(listVo);
        return worksListVOPage;
    }

    @Override
    public IPage<Works> page(IPage<Works> page, WorksSearchDTO worksSearchDTO) {
        QueryWrapper<Works> qw = new QueryWrapper<>();

        String sort = worksSearchDTO.getSort() == null ? getSort(0) : getSort(worksSearchDTO.getSort());
        if (null != worksSearchDTO.getOrder() && worksSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }

        return super.page(page, qw);
    }

    @Override
    public WorksDetailVO WorksDetailVOById(Serializable id) {

        Works info = super.getById(id);
        if(null == info) {
            return null;
        }


        WorksDetailVO vo = new WorksDetailVO();

        vo.setId(info.getId());
        vo.setTitle(info.getTitle());
        vo.setKeywords(info.getKeywords());
        vo.setDescription(info.getDescription());
        vo.setAddtime(info.getAddtime());
        vo.setTag(Arrays.asList(info.getTag().split(",")));
        vo.setContent(info.getContent());
        vo.setHit(info.getHit());


        return vo;
    }

    @Override
    public WorksDetailVO WorksDetailVOByIdAndAddHit(Serializable id) {
        WorksDetailVO vo = WorksDetailVOById(id);
        if(null == vo) {
            return null;
        }

        Works works = new Works();
        works.setId(vo.getId());
        // 浏览量 +1
        works.setHit(vo.getHit() + 1);

        this.updateById(works);

        return vo;
    }

}
