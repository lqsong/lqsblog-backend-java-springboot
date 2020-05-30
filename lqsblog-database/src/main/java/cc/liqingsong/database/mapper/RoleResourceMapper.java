package cc.liqingsong.database.mapper;

import cc.liqingsong.database.entity.RoleResource;
import cc.liqingsong.database.vo.admin.UserResourceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RoleResource Mapper
 * @author liqingsong
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {


    /**
     * 根据角色 ID 查询用户资源 VO
     * @param  roleIds 角色id
     * @return
     */
    List<UserResourceVO> selectUserResourceVO(@Param("roleIds") List<Object> roleIds);


}
