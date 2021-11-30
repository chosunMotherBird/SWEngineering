package com.swteam6.EVCharger.service;

import com.swteam6.EVCharger.domain.charger.ChargerDto;
import com.swteam6.EVCharger.domain.charger.ChargerEntity;
import com.swteam6.EVCharger.repository.ChargerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargerServiceImpl implements ChargerService {

    private final ChargerRepository repository;

    @Override
    public List<ChargerDto.Response> getAllChargers() throws Exception {
        List<ChargerEntity> entityList = repository.findAll();
        List<ChargerDto.Response> dtoList = new ArrayList<>();
        for (ChargerEntity entity : entityList) {
            dtoList.add(new ChargerDto.Response(entity));
        }
        return dtoList;
    }

    @Override
    public ChargerDto.Response findChargerById(Long id) throws Exception {
        return new ChargerDto.Response(repository.findById(id).orElseThrow());
    }

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
