package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.Link;
import cc.liqingsong.database.vo.admin.StatsDayTotalVO;
import cc.liqingsong.database.vo.pc.LinkVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Link Mapper
 * @author liqingsong
 */
public interface LinkMapper extends BaseMapper<Link> {

    /**
     * 返回每月新增数
     *
     * @param createTimeStart  起始创建日期
     * @param createTimeEnd  截止创建日期
     * @return
     */
    List<StatsDayTotalVO> getStatsDayTotalVO(@Param("createTimeStart") LocalDate createTimeStart, @Param("createTimeEnd") LocalDate createTimeEnd);

    /**
     * 根据分类id搜索返回链接列表
     *
     * @param ids  ID数组
     * @return
     */
    List<LinkVO> getPcLinkVOByCategoryId(@Param("ids") List<Object> ids);


}
