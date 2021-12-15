package com.swteam6.EVCharger.service;

import com.swteam6.EVCharger.domain.charger.ChargerDto;
import com.swteam6.EVCharger.domain.charger.ChargerEntity;
import com.swteam6.EVCharger.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Song Hak Hyeon
 * Charger(충전소)에 관한 logic을 처리하는 Service Layer 입니다.
 */
@Service
@RequiredArgsConstructor
public class ChargerServiceImpl implements ChargerService {

    private final ChargerRepository repository;

    /**
     * DB에서 전체 충전소 목록을 찾아 반환함.
     */
    @Override
    public List<ChargerDto.Response> getAllChargers() throws Exception {
        List<ChargerEntity> entityList = repository.findAll();
        List<ChargerDto.Response> dtoList = new ArrayList<>();
        for (ChargerEntity entity : entityList) {
            dtoList.add(new ChargerDto.Response(entity));
        }
        return dtoList;
    }

    /**
     * DB에서 id 값을 기준으로 해당하는 충전소 한 개를 찾아 반환함.
     */
    @Override
    public ChargerDto.Response findChargerById(Long id) throws Exception {
        return new ChargerDto.Response(repository.findById(id).orElseThrow());
    }

    /**
     * DB에서 address 값을 기준으로 해당하는 충전소 목록을 찾아 반환함.
     */
    @Override
    public List<ChargerDto.Response> findAllChargerByAddress(ChargerDto.SearchByAddressRequest dto) throws Exception {
        List<ChargerEntity> tempList = repository.findByAddressContaining(dto.getAddress());
        List<ChargerDto.Response> dtoList = new ArrayList<>();
        for (ChargerEntity entity : tempList) {
            dtoList.add(new ChargerDto.Response(entity));
        }
        return dtoList;
    }
}
