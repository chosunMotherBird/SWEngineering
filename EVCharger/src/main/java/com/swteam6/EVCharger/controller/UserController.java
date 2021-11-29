package com.swteam6.EVCharger.controller;

import com.swteam6.EVCharger.domain.user.UserDto;
import com.swteam6.EVCharger.domain.user.UserEntity;
import com.swteam6.EVCharger.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto.Response createUser(@RequestBody UserDto.SignUpRequest dto) throws Exception {
        return new UserDto.Response(userService.create(dto));
    }
}
