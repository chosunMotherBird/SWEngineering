package com.swteam6.EVCharger.controller;

import com.google.gson.Gson;
import com.swteam6.EVCharger.domain.charger.ChargerDto;
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
class ChargerControllerTest {

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

    // path: /chargers/{id}
    @DisplayName("id 값으로 충전소 단일 조회 테스트")
    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/chargers/1"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("충전소 전체 조회 테스트")
    @Test
    void findAllCharger() throws Exception {
        mockMvc.perform(get("/chargers/all"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Client 에서 입력한 address 값으로 충전소 조회 테스트")
    @Test
    void findAllChargerByAddress() throws Exception {
        // given
        ChargerDto.SearchByAddressRequest request = new ChargerDto.SearchByAddressRequest("서구");
        Gson gson = new Gson();
        String content = gson.toJson(request);

        // when
        mockMvc.perform(post("/chargers/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("도로명 주소 조회 실패 - Client 에서 입력한 address 값이 null 인 경우")
    @Test
    void findAllChargerByAddressFailed() throws Exception {
        // given
        ChargerDto.SearchByAddressRequest request = new ChargerDto.SearchByAddressRequest(null);
        Gson gson = new Gson();
        String content = gson.toJson(request);

        // when
        mockMvc.perform(post("/chargers/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                // then
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }
}