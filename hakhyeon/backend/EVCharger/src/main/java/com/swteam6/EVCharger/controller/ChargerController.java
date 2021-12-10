package com.swteam6.EVCharger.controller;

import com.swteam6.EVCharger.domain.charger.ChargerDto;
import com.swteam6.EVCharger.service.ChargerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    // 도로명 주소 입력 조회
    @PostMapping("/address")
    public ResponseEntity<List<ChargerDto.Response>> findAllChargerByAddress(@Valid @RequestBody ChargerDto.SearchByAddressRequest dto) throws Exception {
        List<ChargerDto.Response> response = chargerService.findAllChargerByAddress(dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
