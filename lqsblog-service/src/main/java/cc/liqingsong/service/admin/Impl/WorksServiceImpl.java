package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.WorksSearchDTO;
import cc.liqingsong.database.entity.Works;
import cc.liqingsong.database.enums.WorksCode;
import cc.liqingsong.database.mapper.WorksMapper;
import cc.liqingsong.database.vo.admin.StatsChartVO;
import cc.liqingsong.database.vo.admin.StatsDayTotalVO;
import cc.liqingsong.database.vo.admin.StatsTotalChartVO;
import cc.liqingsong.service.admin.SearchService;
import cc.liqingsong.service.admin.WorksService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Works 服务实现类
 *
 * @author liqingsong
 */
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements WorksService {

    private SearchService searchService;
    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 排序字段
     */
    private String[] sort = new String[]{"id","hit","addtime"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }


    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(Works entity) {
        if (null == entity) {
            return false;
        }

        saveUpdateVerify(entity);

        entity.setCreateTime(LocalDateTime.now());

        return super.save(entity) && searchService.save(searchService.worksToSearch(entity));
    }

    /**
     * 根据 ID 删除
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {

        Works works = new Works();
        works.setId((Long) id);
        boolean remove = searchService.remove(searchService.worksToSearch(works));

        return super.removeById(id);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(Works entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        saveUpdateVerify(entity);

        return super.updateById(entity) && searchService.updateById(searchService.worksToSearch(entity));
    }


    /**
     * 私用验证
     *
     * @param entity 实体类对象
     */
    private void saveUpdateVerify(Works entity) {

        Assert.fail(null == entity.getTitle(), ResultCode.TITLE_NOT_EMPTY);
        int titleLen = entity.getTitle().length();
        Assert.fail(titleLen > 100 || titleLen < 3, WorksCode.TITLE_LENGTH_WORDS);

        Assert.fail(null == entity.getAddtime() || StringUtils.isEmpty(entity.getAddtime()), WorksCode.ADDTIME_NOT_EMPTY);

        Assert.fail(null == entity.getKeywords(), ResultCode.INCORRECT_PARAMETER);
        int keyLen = entity.getKeywords().length();
        Assert.fail(keyLen > 50, ResultCode.KEYWORDS_LENGTH_WORDS);

        Assert.fail(null == entity.getDescription(), ResultCode.INCORRECT_PARAMETER);
        int desLen = entity.getDescription().length();
        Assert.fail(desLen > 200, ResultCode.DESCRIPTION_LENGTH_WORDS);


        Assert.fail(null == entity.getThumb(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getContent(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getTag(), ResultCode.INCORRECT_PARAMETER);

    }

    @Override
    public IPage<Works> page(IPage<Works> page, WorksSearchDTO worksSearchDTO) {
        QueryWrapper<Works> qw = new QueryWrapper<>();
        if(null != worksSearchDTO.getKeywords()) {
            qw.like("title",worksSearchDTO.getKeywords());
        }

        if(null != worksSearchDTO.getAddtimeend() && "" != worksSearchDTO.getAddtimeend() && null != worksSearchDTO.getAddtimestart() && "" != worksSearchDTO.getAddtimestart()) {
            qw.between("addtime",worksSearchDTO.getAddtimestart(),worksSearchDTO.getAddtimeend());
        }

        if(null != worksSearchDTO.getTags()) {
            qw.like("tag", worksSearchDTO.getTags());
        }

        String sort = worksSearchDTO.getSort() == null ? getSort(0) : getSort(worksSearchDTO.getSort());
        if (null != worksSearchDTO.getOrder() && worksSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }

        return super.page(page, qw);
    }

    @Override
    public StatsTotalChartVO getStatsTotalChartVO() {
        // 当前日期
        LocalDate now = LocalDate.now();
        // 后一天日期
        LocalDate dayAfter = now.plusDays(1);
        // 获取本周几
        int weekValue = now.getDayOfWeek().getValue();
        // 获取本周一日期
        LocalDate monday = now.minusDays(weekValue - 1);
        // 7天前日期
        LocalDate sevenDaysAgo = now.minusDays(7);

        // 总数
        int total = super.count();

        // 本周新增
        QueryWrapper<Works> weekQw = new QueryWrapper<>();
        weekQw.ge("create_time",monday);
        weekQw.lt("create_time",dayAfter);
        int week = super.count(weekQw);

        // chart 数据
        ArrayList<String> day = new ArrayList<>(); // 设置最近7天数组
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        day.add(sevenDaysAgo.format(fmt)); // 7天前
        day.add(now.minusDays(6).format(fmt)); // 6天前
        day.add(now.minusDays(5).format(fmt)); // 5天前
        day.add(now.minusDays(4).format(fmt)); // 4天前
        day.add(now.minusDays(3).format(fmt)); // 3天前
        day.add(now.minusDays(2).format(fmt)); // 2天前
        day.add(now.minusDays(1).format(fmt)); // 1天前

        HashMap<String, Integer> list = new HashMap<>();
        List<StatsDayTotalVO> statsDayTotalVO = baseMapper.getStatsDayTotalVO(sevenDaysAgo, now); // 读取前7天数据
        for (StatsDayTotalVO item: statsDayTotalVO) {
            list.put(item.getDay(),item.getNum());
        }

        ArrayList<Integer> num = new ArrayList<>(); // 设置最近7天对应的数量
        for (String dayItem: day) {
            if(null ==list.get(dayItem)) {
                num.add(0);
            } else {
                num.add(list.get(dayItem));
            }
        }

        StatsChartVO chart = new StatsChartVO(); // 设置chart数据
        chart.setDay(day);
        chart.setNum(num);


        StatsTotalChartVO vo = new StatsTotalChartVO();
        vo.setTotal(total);
        vo.setNum(week);
        vo.setChart(chart);
        return vo;
    }
}
