package com.swteam6.EVCharger.domain.charger;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "Charger")
public class ChargerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARGER_ID")
    private Long id;

    private String chargerName;
    private String chargerLocation;
    private String city;
    private String closedDates;
    private String fastChargeType;
    private Integer slowNum;
    private Integer fastNum;
    private String parkingFee;
    private Double lat;
    private Double lon;
    private LocalDateTime updatedDate;
    private String address;
}
