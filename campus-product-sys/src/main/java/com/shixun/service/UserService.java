package com.shixun.service;

import com.shixun.pojo.api.Result;
import com.shixun.pojo.dto.update.UserLoginDTO;
import com.shixun.pojo.dto.update.UserRegisterDTO;

public interface UserService {
    Result<String> register(UserRegisterDTO userRegisterDTO);

    Result<Object> login(UserLoginDTO userLoginDTO);
}
