package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.LinkSearchDTO;
import cc.liqingsong.database.entity.Link;
import cc.liqingsong.database.entity.LinkCategory;
import cc.liqingsong.database.enums.LinkCode;
import cc.liqingsong.database.mapper.LinkMapper;
import cc.liqingsong.database.vo.admin.*;
import cc.liqingsong.service.admin.LinkCategoryService;
import cc.liqingsong.service.admin.LinkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Link 服务实现类
 *
 * @author liqingsong
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    private LinkCategoryService linkCategoryService;
    @Autowired
    public void setLinkCategoryService(LinkCategoryService linkCategoryService) {
        this.linkCategoryService = linkCategoryService;
    }


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
    public boolean save(Link entity) {

        if (null == entity) {
            return false;
        }

        saveUpdateVerify(entity);

        entity.setCreateTime(LocalDateTime.now());


        return super.save(entity);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    @Override
    public boolean updateById(Link entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        saveUpdateVerify(entity);

        return super.updateById(entity);
    }


    /**
     * 私用验证
     *
     * @param entity 实体类对象
     */
    private void saveUpdateVerify(Link entity) {

        Assert.fail(null == entity.getTitle(), ResultCode.TITLE_NOT_EMPTY);
        int titleLen = entity.getTitle().length();
        Assert.fail(titleLen > 50 || titleLen < 5, LinkCode.TITLE_LENGTH_WORDS);

        Assert.fail(null == entity.getCategoryId(), LinkCode.CATEGORY_ID_NOT_EMPTY);

        Assert.fail(null == entity.getDescription(), ResultCode.INCORRECT_PARAMETER);
        int desLen = entity.getDescription().length();
        Assert.fail(desLen > 100, ResultCode.DESCRIPTION_LENGTH_WORDS);


        Assert.fail(null == entity.getLogo(), ResultCode.INCORRECT_PARAMETER);
        Assert.fail(null == entity.getHref(), ResultCode.INCORRECT_PARAMETER);

    }


    @Override
    public IPage<LinkListVO> listVOPage(IPage<Link> page, LinkSearchDTO linkSearchDTO) {
        // 读取文章分页数据
        IPage<Link> list = page(page, linkSearchDTO);

        // 设置返回数据
        Page<LinkListVO> linkListVOPage = new Page<>();
        linkListVOPage.setCurrent(list.getCurrent());
        linkListVOPage.setTotal(list.getTotal());
        linkListVOPage.setPages(list.getPages());
        linkListVOPage.setSize(list.getSize());
        linkListVOPage.setOrders(list.orders());
        linkListVOPage.setOptimizeCountSql(list.optimizeCountSql());
        linkListVOPage.setSearchCount(list.isSearchCount());

        if(list.getRecords().isEmpty()) {
            linkListVOPage.setRecords(new ArrayList<>());
            return linkListVOPage;
        }


        // 取出分类id
        List<Long> cIds = new ArrayList<>();
        for (Link item : list.getRecords() ) {
            cIds.add(item.getCategoryId());
        }

        // 设置分类
        Map<Long, Object> categorys = new HashMap<>();
        List<LinkCategory> linkCategories = linkCategoryService.listByIds(cIds);
        for ( LinkCategory category: linkCategories ) {
            Map<String, Object> c = new HashMap<>();
            c.put("id",category.getId());
            c.put("name",category.getName());
            c.put("alias",category.getAlias());

            categorys.put(category.getId(),c);
        }

        // 设置数据
        List<LinkListVO> listVo= new ArrayList<>();
        for (Link item2 : list.getRecords() ) {
            LinkListVO aListVo = new LinkListVO();
            aListVo.setId(item2.getId());
            aListVo.setTitle(item2.getTitle());
            aListVo.setCategory(categorys.get(item2.getCategoryId()));
            aListVo.setHit(item2.getHit());
            listVo.add(aListVo);
        }


        linkListVOPage.setRecords(listVo);
        return linkListVOPage;
    }

    @Override
    public IPage<Link> page(IPage<Link> page, LinkSearchDTO linkSearchDTO) {
        QueryWrapper<Link> qw = new QueryWrapper<>();
        if(null != linkSearchDTO.getKeywords()) {
            qw.like("title",linkSearchDTO.getKeywords());
        }

        if(null != linkSearchDTO.getCategoryid()) {
            qw.eq("category_id",linkSearchDTO.getCategoryid());
        }


        String sort = linkSearchDTO.getSort() == null ? getSort(0) : getSort(linkSearchDTO.getSort());
        if (null != linkSearchDTO.getOrder() && linkSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }

        return super.page(page, qw);
    }

    @Override
    public LinkVO getLinkVOById(Long id) {
        Link link = super.getById(id);
        if(null == link) {
            return null;
        }

        LinkCategory linkCategory = linkCategoryService.getById(link.getCategoryId());

        LinkVO linkVO = new LinkVO();
        linkVO.setId(link.getId());
        linkVO.setTitle(link.getTitle());
        linkVO.setCategory(linkCategory);
        linkVO.setDescription(link.getDescription());
        linkVO.setLogo(link.getLogo());
        linkVO.setHref(link.getHref());

        return linkVO;
    }

    @Override
    public StatsTotalChartVO getStatsTotalChartVO() {
        // 当前日期
        LocalDate now = LocalDate.now();
        // 获取当前年
        int nowYear = now.getYear();
        // 获取当前月
        int nowMonthValue = now.getMonthValue();
        String nowMonthValueStr = nowMonthValue < 10 ? "0" + nowMonthValue : String.valueOf(nowMonthValue);
        // 今年1月1日
        LocalDate year = LocalDate.parse(nowYear + "-01-01");
        // 今年当月1日
        LocalDate yearMonth = LocalDate.parse(nowYear + "-" + nowMonthValueStr + "-01");
        // 明年1月1日
        LocalDate yearAfter = LocalDate.parse(now.plusYears(1).getYear() + "-01-01");
        // 12 月前日期
        LocalDate twelveMothsAgo = now.minusMonths(12);
        // 12 月前 1号日期
        LocalDate twelveMothsDays = LocalDate.parse(twelveMothsAgo.getYear() + "-" + nowMonthValueStr + "-01");


        // 总数
        int total = super.count();

        // 本年新增
        QueryWrapper<Link> yearQw = new QueryWrapper<>();
        yearQw.ge("create_time",year);
        yearQw.lt("create_time",yearAfter);
        int yearCount = super.count(yearQw);

        // chart 数据
        ArrayList<String> day = new ArrayList<>(); // 设置最近12月数组
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
        day.add(twelveMothsAgo.format(fmt)); // 12月前
        day.add(now.minusMonths(11).format(fmt)); // 11月前
        day.add(now.minusMonths(10).format(fmt)); // 10月前
        day.add(now.minusMonths(9).format(fmt)); // 9月前
        day.add(now.minusMonths(8).format(fmt)); // 8月前
        day.add(now.minusMonths(7).format(fmt)); // 7月前
        day.add(now.minusMonths(6).format(fmt)); // 6月前
        day.add(now.minusMonths(5).format(fmt)); // 5月前
        day.add(now.minusMonths(4).format(fmt)); // 4月前
        day.add(now.minusMonths(3).format(fmt)); // 3月前
        day.add(now.minusMonths(2).format(fmt)); // 2月前
        day.add(now.minusMonths(1).format(fmt)); // 1月前
        System.out.println(day);


        HashMap<String, Integer> list = new HashMap<>();
        List<StatsDayTotalVO> statsDayTotalVO = baseMapper.getStatsDayTotalVO(twelveMothsDays, yearMonth); // 读取最近12月数据
        for (StatsDayTotalVO item: statsDayTotalVO) {
            list.put(item.getDay(),item.getNum());
        }

        ArrayList<Integer> num = new ArrayList<>(); // 设置最近12月对应的数量
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
        vo.setNum(yearCount);
        vo.setChart(chart);
        return vo;
    }
}
