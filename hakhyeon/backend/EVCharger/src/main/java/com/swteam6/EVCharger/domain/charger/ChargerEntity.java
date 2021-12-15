package com.swteam6.EVCharger.domain.charger;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Author: Song Hak Hyeon
 * DB에 들어가는 Charger Table에 대한 Domain 객체
 * ORM 프레임워크인 JPA의 기능을 활용하여 객체의 틀을 가지고 DB에 해당 table을 자동 생성합니다.
 */
@Entity
@NoArgsConstructor // Lombok 라이브러리에서 제공하는 기본 생성자 자동 생성 기능
@Getter // Lombok 라이브러리에서 제공하는 getter 메소드 자동 생성 기능
@Table(name = "Charger")
public class ChargerEntity {

    @Id // DB의 primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA의 primary key 전략
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
    @NotEmpty // hibernate에서 제공하는 유효성 검사를 위한 애노테이션
    private String address;

    // @Builder 패턴으로 생성자 대신 객체를 유연하게 생성함
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

}
