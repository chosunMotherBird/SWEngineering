package com.swteam6.EVCharger.service;

import com.swteam6.EVCharger.domain.user.UserDto;
import com.swteam6.EVCharger.domain.user.UserEntity;

public interface UserService {

    UserEntity create(UserDto.SignUpRequest dto) throws Exception;

    boolean isExistedEmail(UserDto.SignUpRequest dto) throws Exception;

    UserEntity login(UserDto.LoginRequest dto) throws Exception;
}
