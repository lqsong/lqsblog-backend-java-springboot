package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.Topics;
import cc.liqingsong.database.vo.admin.StatsDayTotalVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Topics Mapper
 * @author liqingsong
 */
public interface TopicsMapper extends BaseMapper<Topics> {

    /**
     * 返回每天新增数
     *
     * @param createTimeStart  起始创建日期
     * @param createTimeEnd  截止创建日期
     * @return
     */
    List<StatsDayTotalVO> getStatsDayTotalVO(@Param("createTimeStart") LocalDate createTimeStart, @Param("createTimeEnd") LocalDate createTimeEnd);

}
