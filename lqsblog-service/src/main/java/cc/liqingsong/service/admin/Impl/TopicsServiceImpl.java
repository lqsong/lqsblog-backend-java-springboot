package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.TopicsSearchDTO;
import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.enums.TopicsCode;
import cc.liqingsong.database.mapper.TopicsMapper;
import cc.liqingsong.database.vo.admin.StatsChartVO;
import cc.liqingsong.database.vo.admin.StatsDayTotalVO;
import cc.liqingsong.database.vo.admin.StatsTotalChartVO;
import cc.liqingsong.service.admin.TopicsService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Topics 服务实现类
 *
 * @author liqingsong
 */
@Service
public class TopicsServiceImpl  extends ServiceImpl<TopicsMapper, Topics> implements TopicsService {

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
     * @param entity 实体类对象
     */
    @Override
    public boolean save(Topics entity) {
        if (null == entity) {
            return false;
        }

        saveUpdateVerify(entity,"save");

        entity.setContentStr(JSON.toJSONString(entity.getContent()));
        entity.setCreateTime(LocalDateTime.now());

        return super.save(entity);
    }


    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean updateById(Topics entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        saveUpdateVerify(entity, "update");

        entity.setContentStr(JSON.toJSONString(entity.getContent()));

        return super.updateById(entity);
    }

    /**
     * 根据 ID 获取信息
     *
     * @param id ID
     */
    @Override
    public Topics getById(Serializable id) {
        Topics topics = super.getById(id);
        if(null == topics) {
            return topics;
        }

        topics.setContent(JSON.parseArray(topics.getContentStr()));
        topics.setContentStr(null);

        return topics;
    }


    /**
     * 私用验证
     *
     * @param entity 实体类对象
     */
    private void saveUpdateVerify(Topics entity, String type) {

        Assert.fail(null == entity.getTitle(), ResultCode.TITLE_NOT_EMPTY);
        int titleLen = entity.getTitle().length();
        Assert.fail(titleLen > 50 || titleLen < 3, TopicsCode.TITLE_LENGTH_WORDS);

        Assert.fail(null == entity.getAlias(), TopicsCode.ALIAS_NOT_EMPTY);
        int aliasLen = entity.getAlias().length();
        Assert.fail(aliasLen > 10 || aliasLen < 1, TopicsCode.ALIAS_LENGTH_WORDS);

        Assert.fail(null == entity.getKeywords(), ResultCode.INCORRECT_PARAMETER);
        int keyLen = entity.getKeywords().length();
        Assert.fail(keyLen > 50, ResultCode.KEYWORDS_LENGTH_WORDS);

        Assert.fail(null == entity.getDescription(), ResultCode.INCORRECT_PARAMETER);
        int desLen = entity.getDescription().length();
        Assert.fail(desLen > 200, ResultCode.DESCRIPTION_LENGTH_WORDS);


        Assert.fail(null == entity.getAddtime() || StringUtils.isEmpty(entity.getAddtime()), TopicsCode.ADDTIME_NOT_EMPTY);
        Assert.fail(null == entity.getContent(), ResultCode.INCORRECT_PARAMETER);


        QueryWrapper<Topics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("alias",entity.getAlias());
        switch(type) {
            case "update" :
                queryWrapper.ne("id", entity.getId());
                break;
            default :
        }
        int count = super.count(queryWrapper);
        Assert.fail( count > 0, TopicsCode.ALIAS_THE_SAME);

    }


    @Override
    public IPage<Topics> page(IPage<Topics> page, TopicsSearchDTO topicsSearchDTO) {
        QueryWrapper<Topics> qw = new QueryWrapper<>();
        if (null != topicsSearchDTO.getKeywords()) {
            qw.like("title",topicsSearchDTO.getKeywords()).or().like("alias",topicsSearchDTO.getKeywords());
        }

        String sort = topicsSearchDTO.getSort() == null ? getSort(0) : getSort(topicsSearchDTO.getSort());
        if (null != topicsSearchDTO.getOrder() && topicsSearchDTO.getOrder() == 1) {
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
        // 获取本月几号
        int dayOfMonth = now.getDayOfMonth();
        // 获取本1号日期
        LocalDate monday = now.minusDays(dayOfMonth - 1);

        // 总数
        int total = super.count();

        // 本月新增
        QueryWrapper<Topics> monthQw = new QueryWrapper<>();
        monthQw.ge("create_time",monday);
        monthQw.lt("create_time",dayAfter);
        int month = super.count(monthQw);

        // chart 数据
        ArrayList<String> day = new ArrayList<>(); // 设置最近30天数组
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 30; i>0; i--) {
            day.add(now.minusDays(i).format(fmt)); // i天前
        }

        HashMap<String, Integer> list = new HashMap<>();
        List<StatsDayTotalVO> statsDayTotalVO = baseMapper.getStatsDayTotalVO(LocalDate.parse(day.get(0)), now); // 读取最近30天数据
        for (StatsDayTotalVO item: statsDayTotalVO) {
            list.put(item.getDay(),item.getNum());
        }

        ArrayList<Integer> num = new ArrayList<>(); // 设置最近30天对应的数量
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
        vo.setNum(month);
        vo.setChart(chart);
        return vo;
    }
}
