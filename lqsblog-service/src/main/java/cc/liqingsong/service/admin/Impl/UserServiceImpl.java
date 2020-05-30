package cc.liqingsong.service.admin.Impl;

import cc.liqingsong.common.entity.Assert;
import cc.liqingsong.common.enums.ResultCode;
import cc.liqingsong.database.dto.admin.LoginDTO;
import cc.liqingsong.database.dto.admin.UserDTO;
import cc.liqingsong.database.dto.admin.UserSearchDTO;
import cc.liqingsong.database.entity.User;
import cc.liqingsong.database.entity.UserRole;
import cc.liqingsong.database.enums.UserCode;
import cc.liqingsong.database.mapper.UserMapper;
import cc.liqingsong.database.vo.admin.*;
import cc.liqingsong.service.admin.RoleResourceService;
import cc.liqingsong.service.admin.UserRoleService;
import cc.liqingsong.service.admin.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;


/**
 * User 服务实现类
 *
 * @author liqingsong
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserRoleService userRoleService;
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    private RoleResourceService roleResourceService;
    @Autowired
    public void setRoleResourceService(RoleResourceService roleResourceService) {
        this.roleResourceService = roleResourceService;
    }

    /**
     * 排序字段
     */
    private String[] sort = new String[]{"id"};
    public String getSort(int i ) {
        int length = sort.length - 1;
        return i > length ? sort[0] : sort[i];
    }




    @Override
    public User selectByUserName(String username) {
        User user = new User();
        user.setUsername(username);
        return super.getOne(Wrappers.<User>query().eq("username", username), false);
    }

    @Override
    public User loginUser(LoginDTO loginDTO) {

        Assert.fail(null == loginDTO.getUsername(), UserCode.LOGIN_USERNAME_NOT_EMPTY);

        Assert.fail(null == loginDTO.getPassword(), UserCode.LOGIN_PASSWORDS_NOT_EMPTY);

        User user = selectByUserName(loginDTO.getUsername());
        Assert.fail(null == user, UserCode.LOGIN_USERNAME_PASSWORDS_ERR);


        String password = passWordSimpleHash(loginDTO.getPassword(), user.getSalt());

        Assert.fail(!user.getPassword().equals(password), UserCode.LOGIN_USERNAME_PASSWORDS_ERR);

        return user;
    }

    @Override
    public LoginUserVO setLoginUserVO(User user) {

        List<UserRoleVO> userRole = userRoleService.listVO(user.getId());

        List<Object> roleIds = new ArrayList<>();
        List<String> roles = new ArrayList<>();
        for ( UserRoleVO item : userRole ) {
            roles.add(item.getName());
            roleIds.add(item.getId());
        }

        List<String> resources = new ArrayList<>();
        if(!roleIds.isEmpty()) {
            List<UserResourceVO> userResource = roleResourceService.listUserResourceVO(roleIds);
            for (UserResourceVO item2 : userResource) {
                resources.add(item2.getUrlcode());
            }
        }

        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setName(user.getNickname())
                .setAvatar("")
                .setResources(resources)
                .setRoles(roles)
                .setMsgtotal((long) 0);

        return loginUserVO;
    }

    @Override
    public IPage<UserVO> userVOPage(IPage<User> page, UserSearchDTO userSearchDTO) {
        // 读取分页数据
        IPage<User> list = page(page, userSearchDTO);

        // 设置返回数据
        Page<UserVO> userVOPage = new Page<>();
        userVOPage.setCurrent(list.getCurrent());
        userVOPage.setTotal(list.getTotal());
        userVOPage.setPages(list.getPages());
        userVOPage.setSize(list.getSize());
        userVOPage.setOrders(list.orders());
        userVOPage.setOptimizeCountSql(list.optimizeCountSql());
        userVOPage.setSearchCount(list.isSearchCount());

        if(list.getRecords().isEmpty()) {
            userVOPage.setRecords(new ArrayList<>());
            return userVOPage;
        }

        // 取出user_id
        List<Long> userIds = new ArrayList<>();
        for (User item : list.getRecords()) {
            userIds.add(item.getId());
        }

        // 设置用户对应角色
        Map<Long, List<UserRoleVO>> userRoleMap = new HashMap<>();
        List<UserRoleUidVO> userRoleUidVOS = userRoleService.listUserRoleUidVO(userIds);
        for (UserRoleUidVO item : userRoleUidVOS   ) {
            Long userId = item.getUserId();
            List<UserRoleVO> listvo = userRoleMap.get(userId);
            if(null == listvo) {
                listvo = new ArrayList<>();
                listvo.add(item);
            } else {
                listvo.add(item);
            }
            userRoleMap.put(userId,listvo);
        }

        // 设置数据

        List<UserVO> listVo = new ArrayList<>();
        for (User item2 : list.getRecords()) {
            UserVO userVO = new UserVO();
            userVO.setId(item2.getId());
            userVO.setNickname(item2.getNickname());
            userVO.setUsername(item2.getUsername());
            userVO.setLocked(item2.getLocked());
            userVO.setRoles(userRoleMap.get(item2.getId()));
            listVo.add(userVO);
        }

        userVOPage.setRecords(listVo);
        return userVOPage;
    }

    @Override
    public IPage<User> page(IPage<User> page, UserSearchDTO userSearchDTO) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        if(null != userSearchDTO.getKeywords()) {
            qw.like("username",userSearchDTO.getKeywords()).or().like("nickname",userSearchDTO.getKeywords());
        }

        String sort = userSearchDTO.getSort() == null ? getSort(0) : getSort(userSearchDTO.getSort());
        if (null != userSearchDTO.getOrder() && userSearchDTO.getOrder() == 1) {
            qw.orderByAsc(sort);
        } else {
            qw.orderByDesc(sort);
        }

        return super.page(page, qw);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(UserDTO entity) {

        if (null == entity) {
            return false;
        }

        saveUpdateVerify(entity, "save");

        String salt = saltRandom(8);
        String password = passWordSimpleHash(entity.getPassword(), salt);

        User user = new User();
        user.setNickname(entity.getNickname());
        user.setUsername(entity.getUsername());
        user.setSalt(salt);
        user.setPassword(password);

        // 必须先执行
        boolean save = super.save(user);

        entity.setId(user.getId());
        // 需要 entity.getId();
        boolean saveBatch = saveBatchUserRole(entity);

        return save && saveBatch;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(UserDTO entity) {

        if (null == entity) {
            return false;
        }

        Assert.fail(null == entity.getId(), ResultCode.ID_NOT_FOUND);

        saveUpdateVerify(entity, "update");



        User user = new User();
        user.setId(entity.getId());
        user.setNickname(entity.getNickname());
        user.setUsername(entity.getUsername());

        if(!entity.getPassword().equals("")) {
            String salt = saltRandom(8);
            String password = passWordSimpleHash(entity.getPassword(), salt);
            user.setSalt(salt);
            user.setPassword(password);
        }

        return super.updateById(user) && saveBatchUserRole(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatchUserRole(UserDTO entity) {
        Assert.fail(null == entity, ResultCode.ID_REQUIRED);
        Assert.fail(null == entity.getId(), ResultCode.ID_REQUIRED);

        // 先清空同用户ID下数据
        boolean b = userRoleService.removeByUserId(entity.getId());

        boolean saveBatch = true;

        List<Long> roles = entity.getRoles();
        if (roles.size() > 0) {
            // 再批量添加
            ArrayList<UserRole> urlist = new ArrayList<>();
            for (Long item : roles ) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(item);
                userRole.setUserId(entity.getId());
                urlist.add(userRole);
            }
            saveBatch = userRoleService.saveBatch(urlist);
        }

        return saveBatch;
    }

    @Override
    public UserVO getUserVOById(Serializable id) {
        Assert.fail(null == id, ResultCode.ID_REQUIRED);

        User user = super.getById(id);
        Assert.fail(null == user, ResultCode.ID_NOT_FOUND);

        List<UserRoleVO> roles = userRoleService.listVO(user.getId());

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setLocked(user.getLocked());
        userVO.setNickname(user.getNickname());
        userVO.setUsername(user.getUsername());
        userVO.setRoles(roles);
        return userVO;
    }


    @Override
    public String passWordSimpleHash(String password, String salt) {
        return new Md5Hash(password, salt, 3).toString();
    }

    @Override
    public String saltRandom(int num) {
        return UUID.randomUUID().toString().substring(0, num);
    }


    /**
     * 私用验证
     *
     * @param entity 实体类对象
     * @param type 类型  save、update
     */
    private void saveUpdateVerify(User entity, String type) {

        Assert.fail(null == entity.getUsername(), UserCode.LOGIN_USERNAME_NOT_EMPTY);
        int nameLen = entity.getUsername().length();
        Assert.fail(nameLen > 30 || nameLen < 6, UserCode.USERNAME_LENGTH_WORDS);

        Assert.fail(null == entity.getPassword(), UserCode.LOGIN_PASSWORDS_NOT_EMPTY);
        if (type.equals("save") || !entity.getPassword().equals("")) {
            int passLen = entity.getPassword().length();
            Assert.fail(passLen > 15 || passLen < 6, UserCode.PASSWORDS_LENGTH_WORDS);
        }

        Assert.fail(null == entity.getNickname(), ResultCode.INCORRECT_PARAMETER);

    }

    /**
     * 删除一条记录根据id
     *
     * @param id ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {

        // 清空同用户ID下数据
        boolean b = userRoleService.removeByUserId(id);

        return super.removeById(id);
    }
}
