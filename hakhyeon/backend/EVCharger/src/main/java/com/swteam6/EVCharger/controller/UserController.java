package com.swteam6.EVCharger.controller;

import com.swteam6.EVCharger.domain.user.UserDto;
import com.swteam6.EVCharger.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    // 회원 가입
    @PostMapping
    public ResponseEntity<UserDto.Response> createUser(@Valid @RequestBody UserDto.SignUpRequest dto) throws Exception {
        UserDto.Response response = new UserDto.Response(userService.create(dto));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

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
