package com.swteam6.EVCharger.repository;

import com.swteam6.EVCharger.domain.charger.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargerRepository extends JpaRepository<ChargerEntity, Long> {
    // LIKE query: %in%
    List<ChargerEntity> findByAddressContaining(String address);
}
