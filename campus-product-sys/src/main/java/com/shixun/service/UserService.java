package com.shixun.service;



import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.query.extend.UserQueryDto;
import com.shixun.pojo.dto.update.UserLoginDTO;
import com.shixun.pojo.dto.update.UserRegisterDTO;
import com.shixun.pojo.dto.update.UserUpdateDTO;
import com.shixun.pojo.entity.User;
import com.shixun.pojo.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * 用户服务类
 */
public interface UserService {
    Result<String> register(UserRegisterDTO userRegisterDTO);

    Result<Object> login(UserLoginDTO userLoginDTO);

    Result<UserVO> auth();

    Result<List<User>> query(UserQueryDto userQueryDto);

    Result<String> update(UserUpdateDTO userUpdateDTO);

    Result<String> batchDelete(List<Integer> ids);

    Result<String> updatePwd(Map<String, String> map);

    Result<UserVO> getById(Integer id);

    Result<String> insert(UserRegisterDTO userRegisterDTO);

    Result<String> backUpdate(User user);

}
