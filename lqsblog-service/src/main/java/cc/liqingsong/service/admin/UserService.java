package cc.liqingsong.service.admin;

import cc.liqingsong.database.dto.admin.LoginDTO;
import cc.liqingsong.database.dto.admin.UserDTO;
import cc.liqingsong.database.dto.admin.UserSearchDTO;
import cc.liqingsong.database.entity.User;
import cc.liqingsong.database.vo.admin.LoginUserVO;
import cc.liqingsong.database.vo.admin.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * User 服务类
 *
 * @author liqingsong
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return
     */
    User selectByUserName(String username);

    /**
     * 登录
     *
     * @param loginDTO 登录信息
     * @return
     */
    User loginUser(LoginDTO loginDTO);

    /**
     * 根据 用户 设置登录的用户信息
     *
     * @param user 用户
     * @return
     */
    LoginUserVO setLoginUserVO(User user);

    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param userSearchDTO  搜索信息
     * @return
     */
    IPage<UserVO> userVOPage(IPage<User> page, UserSearchDTO userSearchDTO);

    /**
     * 根据搜索信息分页
     *
     * @param page 分页对象
     * @param userSearchDTO  搜索信息
     * @return
     */
    IPage<User> page(IPage<User> page, UserSearchDTO userSearchDTO);

    /**
     * 插入一条记录
     *
     * @param entity 实体类对象
     */
    boolean save(UserDTO entity);

    /**
     * 根据 ID 修改
     *
     * @param entity 实体类对象
     */
    boolean updateById(UserDTO entity);


    /**
     * 批量插入用户角色关联表记录
     *
     * @param entity 实体类对象
     */
    boolean saveBatchUserRole(UserDTO entity);

    /**
     * 根据 user_id 获取用户信息
     *
     * @param id  user_id
     */
    UserVO getUserVOById(Serializable id);


    /**
     * 密码加密
     *
     * @param password 未加密的密码
     * @param salt 盐
     * @return
     */
    String passWordSimpleHash(String password, String salt);

    /**
     * 生成随机盐
     *
     * @param num 生成位数
     * @return
     */
    String saltRandom(int num);



}
