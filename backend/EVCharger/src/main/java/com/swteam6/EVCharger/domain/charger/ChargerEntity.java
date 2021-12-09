package com.swteam6.EVCharger.domain.charger;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
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
    private String address;

    @Builder
    public ChargerEntity(String chargerName, String chargerLocation, String city, String closedDates, String fastChargeType,
                         Integer slowNum, Integer fastNum, String parkingFee, Double lat, Double lon, String address) {
        this.chargerName = chargerName;
        this.chargerLocation = chargerLocation;
        this.city = city;
        this.closedDates = closedDates;
        this.fastChargeType = fastChargeType;
        this.slowNum = slowNum;
        this.fastNum = fastNum;
        this.parkingFee = parkingFee;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

//    public ChargerDto toDto() {
//        return ChargerDto.builder()
//                .chargerName(chargerName)
//                .chargerLocation(chargerLocation)
//                .city(city)
//                .closedDates(closedDates)
//                .fastChargeType(fastChargeType)
//                .slowNum(slowNum)
//                .fastNum(fastNum)
//                .parkingFee(parkingFee)
//                .lat(lat)
//                .lon(lon)
//                .address(address)
//                .build();
//    }
}
