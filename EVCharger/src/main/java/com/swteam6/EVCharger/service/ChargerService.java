package com.swteam6.EVCharger.service;

import com.swteam6.EVCharger.domain.charger.ChargerDto;

import java.util.List;

public interface ChargerService {

    // findAll: 검색해서 List 반환
    List<ChargerDto.Response> getAllChargers() throws Exception;

    // findById: id값으로 1개 반환
    ChargerDto.Response findChargerById(Long id) throws Exception;

    // findAllByAddressContains: address값을 포함하는 객체들을 List 반환
    List<ChargerDto.Response> findAllChargerByAddress(ChargerDto.SearchByAddressRequest dto) throws Exception;
}
