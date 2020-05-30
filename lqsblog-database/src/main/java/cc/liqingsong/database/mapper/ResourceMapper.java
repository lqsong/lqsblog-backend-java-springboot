package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.Resource;
import cc.liqingsong.database.vo.admin.CascaderVO;
import cc.liqingsong.database.vo.admin.IdNamePidVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Permission Mapper
 * @author liqingsong
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 根据pid搜索 返回联动列表
     *
     * @param pid 父id
     * @return
     */
    List<CascaderVO> selectCascaderVO(@Param("pid") Long pid);

    /**
     * 返回全部列表
     *
     * @return
     */
    List<IdNamePidVO> selectIdNamePidVO();

}
