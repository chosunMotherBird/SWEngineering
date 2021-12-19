package com.swteam6.EVCharger.service;

import com.swteam6.EVCharger.domain.user.UserDto;
import com.swteam6.EVCharger.domain.user.UserEntity;

/**
 * Author: Song Hak Hyeon
 * User(회원)에 관한 logic을 수행하기 위해 interface로 뼈대를 만들어 둠.
 * 객체 지향의 원칙 중 OCP를 지키기 위해 interface를 통해 추상화를 하였습니다.
 * OCP(Open/Closed Principle) - 확장에는 열려(Open) 있으나, 변경에는 닫혀(Closed)있어야 한다.
 */
public interface UserService {

    UserEntity create(UserDto.SignUpRequest dto) throws Exception;

    boolean isExistedEmail(UserDto.SignUpRequest dto) throws Exception;

    boolean isExistedEmailV2(UserDto.LoginRequest dto) throws Exception;

    UserEntity login(UserDto.LoginRequest dto) throws Exception;
}
