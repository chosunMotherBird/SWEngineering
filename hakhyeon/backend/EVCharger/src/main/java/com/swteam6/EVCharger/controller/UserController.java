package com.swteam6.EVCharger.controller;

import com.swteam6.EVCharger.domain.user.UserDto;
import com.swteam6.EVCharger.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Author: Song Hak Hyeon
 * Client의 User(회원)에 관한 요청을 처리하는 Server의 Controller
 *
 * 회원 가입(createUser) : POST - "/user"
 * 로그인(loginUser) : POST - "/user/login"
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    /**
     * Client에서 입력한 email, userPass, userName, phoneNum 값이 JSON 형식으로 Server에 전달 되고,
     * Server는 해당하는 값들을 parsing 하여 UserDto.SignUpRequest 객체로 생성하여 처리합니다.
     */
    // 회원 가입
    @PostMapping
    public ResponseEntity<UserDto.Response> createUser(@Valid @RequestBody UserDto.SignUpRequest dto) throws Exception {
        UserDto.Response response = new UserDto.Response(userService.create(dto));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Client에서 입력한 email, userPass 값이 JSON 형식으로 Server에 전달 되고,
     * Server는 해당하는 값들을 parsing 하여 UserDto.LoginRequest 객체로 생성하여 처리합니다.
     */
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserDto.Response> loginUser(@Valid @RequestBody UserDto.LoginRequest dto) throws Exception {

        String tempEmail = dto.getEmail();
        String tempPass = dto.getUserPass();
        UserDto.Response response = null;

        if (tempEmail != null && tempPass != null) {
            response = new UserDto.Response(userService.login(dto));
        }
        if (response == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
