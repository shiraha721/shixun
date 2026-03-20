package com.shixun.service.impl;


import com.shixun.context.LocalThreadHolder;
import com.shixun.mapper.UserMapper;
import com.shixun.pojo.api.ApiResult;
import com.shixun.pojo.api.PageResult;
import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.UserQueryDto;
import com.shixun.pojo.dto.update.UserLoginDTO;
import com.shixun.pojo.dto.update.UserRegisterDTO;
import com.shixun.pojo.dto.update.UserUpdateDTO;
import com.shixun.pojo.em.LoginStatusEnum;
import com.shixun.pojo.em.RoleEnum;
import com.shixun.pojo.em.WordStatusEnum;
import com.shixun.pojo.entity.User;
import com.shixun.pojo.vo.UserVO;
import com.shixun.service.UserService;
import com.shixun.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 用户注册
     *
     * @param userRegisterDTO 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> register(UserRegisterDTO userRegisterDTO) {
        User entity = userMapper.getByActive(
                User.builder().userAccount(userRegisterDTO.getUserAccount()).build()
        );
        if (Objects.nonNull(entity)) {
            return ApiResult.error("账号不可用");
        }
        User saveEntity = User.builder()
                .userRole(RoleEnum.USER.getRole())
                .userName(userRegisterDTO.getUserName())
                .userAccount(userRegisterDTO.getUserAccount())
                .userAvatar(userRegisterDTO.getUserAvatar())
                .userPwd(userRegisterDTO.getUserPwd())
                .userEmail(userRegisterDTO.getUserEmail())
                .createTime(LocalDateTime.now())
                .isLogin(LoginStatusEnum.USE.getFlag())
                .isWord(WordStatusEnum.USE.getFlag()).build();
        userMapper.insert(saveEntity);
        return ApiResult.success("注册成功");
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO 登录入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<Object> login(UserLoginDTO userLoginDTO) {
        User user = userMapper.getByActive(
                User.builder().userAccount(userLoginDTO.getUserAccount()).build()
        );
        if (!Objects.nonNull(user)) {
            return ApiResult.error("账号不存在");
        }
        if (!Objects.equals(userLoginDTO.getUserPwd(), user.getUserPwd())) {
            return ApiResult.error("密码错误");
        }
        if (user.getIsLogin()) {
            return ApiResult.error("登录状态异常");
        }
        String token = JwtUtil.toToken(user.getId(), user.getUserRole());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("role", user.getUserRole());
        // 设置上一次登录时间
        User userEntity = new User();
        userEntity.setId(user.getId());
        userEntity.setLastLoginTime(LocalDateTime.now());
        userMapper.update(userEntity);
        return ApiResult.success("登录成功", map);
    }

    /**
     * 令牌检验 -- 认证成功返回用户信息
     *
     * @return Result<UserVO>
     */
    @Override
    public Result<UserVO> auth() {
        Integer userId = LocalThreadHolder.getUserId();
        User queryEntity = User.builder().id(userId).build();
        User user = userMapper.getByActive(queryEntity);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ApiResult.success(userVO);
    }


    /**
     * 分页查询用户数据
     *
     * @param userQueryDto 分页参数
     * @return Result<List < User>> 响应结果
     */
    @Override
    public Result<List<User>> query(UserQueryDto userQueryDto) {
        List<User> users = userMapper.query(userQueryDto);
        Integer count = userMapper.queryCount(userQueryDto);
        return PageResult.success(users, count);
    }

    /**
     * 用户信息修改
     *
     * @param userUpdateDTO 修改信息入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> update(UserUpdateDTO userUpdateDTO) {
        User updateEntity = User.builder().id(LocalThreadHolder.getUserId()).build();
        BeanUtils.copyProperties(userUpdateDTO, updateEntity);
        userMapper.update(updateEntity);
        return ApiResult.success();
    }


    /**
     * 批量删除用户信息
     */
    @Override
    public Result<String> batchDelete(List<Integer> ids) {
        userMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 用户信息修改密码
     *
     * @param map 修改信息入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> updatePwd(Map<String, String> map) {
        String oldPwd = map.get("oldPwd");
        String newPwd = map.get("newPwd");
        String againPwd = map.get("againPwd");
        if (Objects.isNull(oldPwd)) {
            return ApiResult.error("原始密码输入不能为空");
        }
        if (Objects.isNull(newPwd)) {
            return ApiResult.error("请输入新密码");
        }
        if (Objects.isNull(againPwd)) {
            return ApiResult.error("请补充确认密码");
        }
        if (!newPwd.equals(againPwd)) {
            return ApiResult.error("前后密码输入不一致");
        }
        User user = userMapper.getByActive(
                User.builder().id(LocalThreadHolder.getUserId()).build()
        );
        if (!user.getUserPwd().equals(oldPwd)) {
            return ApiResult.error("原始密码验证失败");
        }
        user.setUserPwd(newPwd);
        userMapper.update(user);
        return ApiResult.success();
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     */
    @Override
    public Result<UserVO> getById(Integer id) {
        User user = userMapper.getByActive(User.builder().id(id).build());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ApiResult.success(userVO);
    }

    /**
     * 后台新增用户
     *
     * @param userRegisterDTO 注册入参
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> insert(UserRegisterDTO userRegisterDTO) {
        return register(userRegisterDTO);
    }

    /**
     * 后台用户信息修改
     *
     * @param user 信息实体
     * @return Result<String> 响应结果
     */
    @Override
    public Result<String> backUpdate(User user) {
        userMapper.update(user);
        return ApiResult.success();
    }

}
