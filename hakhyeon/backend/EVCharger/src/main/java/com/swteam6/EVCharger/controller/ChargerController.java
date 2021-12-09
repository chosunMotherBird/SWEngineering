package com.swteam6.EVCharger.controller;

import com.swteam6.EVCharger.domain.charger.ChargerDto;
import com.swteam6.EVCharger.service.ChargerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chargers")
public class ChargerController {

    private final ChargerServiceImpl chargerService;

    // id로 1개 조회
    @GetMapping("/{id}")
    public ChargerDto.Response findById(@PathVariable Long id) throws Exception {
        return chargerService.findChargerById(id);
    }

    // 전체 조회
    @GetMapping("/all")
    public List<ChargerDto.Response> findAllCharger() throws Exception {
        return chargerService.getAllChargers();
    }

    // 주소 입력 조회
    @GetMapping("/address")
    public List<ChargerDto.Response> findAllChargerByAddress(@RequestBody ChargerDto.SearchByAddressRequest dto) throws Exception {
        return chargerService.findAllChargerByAddress(dto);
    }

}
