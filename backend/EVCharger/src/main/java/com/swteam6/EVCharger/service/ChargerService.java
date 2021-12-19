package com.swteam6.EVCharger.service;

import com.swteam6.EVCharger.domain.charger.ChargerDto;

import java.util.List;

/**
 * Author: Song Hak Hyeon
 * Charger(충전소)에 관한 logic을 수행하기 위해 interface로 뼈대를 만들어 둠.
 * 객체 지향의 원칙 중 OCP를 지키기 위해 interface를 통해 추상화를 하였습니다.
 * OCP(Open/Closed Principle) - 확장에는 열려(Open) 있으나, 변경에는 닫혀(Closed)있어야 한다.
 */
public interface ChargerService {

    // findAll: 검색해서 List 반환
    List<ChargerDto.Response> getAllChargers() throws Exception;

    // findById: id값으로 1개 반환
    ChargerDto.Response findChargerById(Long id) throws Exception;

    // findAllByAddressContains: address값을 포함하는 객체들을 List 반환
    List<ChargerDto.Response> findAllChargerByAddress(ChargerDto.SearchByAddressRequest dto) throws Exception;
}
