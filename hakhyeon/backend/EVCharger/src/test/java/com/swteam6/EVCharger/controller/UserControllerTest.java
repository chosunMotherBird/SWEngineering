package com.swteam6.EVCharger.controller;

import com.google.gson.Gson;
import com.swteam6.EVCharger.domain.user.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Controller Layer 에서 테스트 실행 시 연결되어 있는 Service Layer, Repository Layer 까지 한번에 테스트
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        System.out.println("------단위 테스트 실행 시작------");
    }

    @AfterEach
    void afterEach() {
        System.out.println("------단위 테스트 실행 완료------");
        System.out.println();
    }

    @AfterAll
    static void tearDown() {
        System.out.println("------테스트 종료------");
    }

    @DisplayName("회원가입 성공")
    @Test
    @Order(1)
    void createUserSuccess() throws Exception {
        // given
        UserDto.SignUpRequest signUpRequest = new UserDto.SignUpRequest("test@test.com", "testPassword", "testName", "010-1111-1111");
        Gson gson = new Gson();
        String content = gson.toJson(signUpRequest);

        // when
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    // 하나라도 null 이면 회원가입 실패
    @DisplayName("회원가입 실패 - Client 에서 넘어온 parameter 가 null 인 경우")
    @Test
    @Order(2)
    void createUserFailed() throws Exception {
        // given
        UserDto.SignUpRequest signUpRequest = new UserDto.SignUpRequest(null, "testPassword", "testName", "010-1111-1111");
        Gson gson = new Gson();
        String content = gson.toJson(signUpRequest);

        // when
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    // 하나라도 null 이면 회원가입 실패
    @DisplayName("회원가입 실패 - Client 에서 넘어온 email parameter 가 email 형식이 아닌 경우")
    @Test
    @Order(2)
    void createUserFailedV2() throws Exception {
        // given
        UserDto.SignUpRequest signUpRequest = new UserDto.SignUpRequest("test", "testPassword", "testName", "010-1111-1111");
        Gson gson = new Gson();
        String content = gson.toJson(signUpRequest);

        // when
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인 성공")
    @Test
    @Order(3)
    void loginUserSuccess() throws Exception {
        // given
        UserDto.LoginRequest loginRequest = new UserDto.LoginRequest("test@test.com", "testPassword");
        Gson gson = new Gson();
        String content = gson.toJson(loginRequest);

        // when
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인 실패 - Client 에서 넘어온 parameter 가 null 인 경우")
    @Test
    @Order(4)
    void loginUserFailed() throws Exception {
        // given
        UserDto.LoginRequest loginRequest = new UserDto.LoginRequest("null" , "null");
        Gson gson = new Gson();
        String content = gson.toJson(loginRequest);

        // when
        mockMvc.perform(post("/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content))
                // then
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("로그인 실패 - 등록되지 않은 User 로그인")
    @Test
    @Order(4)
    void loginUserFailedV2() throws Exception {
        // given
        UserDto.LoginRequest loginRequest = new UserDto.LoginRequest("test@naver.com" , "1234");
        Gson gson = new Gson();
        String content = gson.toJson(loginRequest);

        // when
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }
}