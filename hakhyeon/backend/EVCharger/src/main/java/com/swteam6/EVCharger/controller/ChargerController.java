package com.swteam6.EVCharger.controller;

import com.swteam6.EVCharger.domain.charger.ChargerDto;
import com.swteam6.EVCharger.service.ChargerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Author: Song Hak Hyeon
 * Client의 Charger(충전소)에 관한 요청을 처리하는 Server의 Controller
 *
 * id 값으로 충전소 단일 조회(findById) : GET - "/chargers/{id}"
 * 충전소 전체 조회(findAllCharger) : GET - "/chargers/all"
 * Client에서 입력한 address(도로명 주소)값으로 해당 입력 값을 포함하는 충전소 검색(findAllChargerByAddress) : POST - "/chargers/address"
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/chargers")
public class ChargerController {

    private final ChargerServiceImpl chargerService;

    /**
     * Client는 충전소 마다 부여된 id 값(기본키)을 url로 넣어 요청해야 함
     */
    // id로 1개 조회
    @GetMapping("/{id}")
    public ChargerDto.Response findById(@PathVariable Long id) throws Exception {
        return chargerService.findChargerById(id);
    }

    /**
     * DB에 저장된 전체 충전소를 List 형식으로 반환합니다.
     */
    // 전체 조회
    @GetMapping("/all")
    public List<ChargerDto.Response> findAllCharger() throws Exception {
        return chargerService.getAllChargers();
    }

    /**
     * Client에서 입력한 address 값이 JSON 형식으로 Server에 전달 되고,
     * Server는 해당하는 값들을 parsing 하여 ChargerDto.SearchByAddressRequest 객체로 생성하여 처리합니다.
     */
    // 도로명 주소 입력 조회
    @PostMapping("/address")
    public ResponseEntity<List<ChargerDto.Response>> findAllChargerByAddress(@Valid @RequestBody ChargerDto.SearchByAddressRequest dto) throws Exception {
        List<ChargerDto.Response> response = chargerService.findAllChargerByAddress(dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
