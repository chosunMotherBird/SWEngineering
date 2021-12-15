package com.swteam6.EVCharger.domain.charger;

import lombok.*;

/**
 * Author: Song Hak Hyeon
 * DB에 직접 값을 넣는 Entity는 불변 객체로 두고,
 * 이와 별개로 Client와 Server간의 데이터 교환을 위해 DTO(Data Transfer Object)를 구현함.
 * 해당하는 Request/Response 마다 Dto 클래스를 만들지 않고 inner class 구조로 유지/보수 하기 편하게 설계하였습니다.
 */
@Data
@NoArgsConstructor
public class ChargerDto {

    // Client의 도로명 주소 검색 기능을 수행하기 위한 dto
    @Getter
    @NoArgsConstructor
    public static class SearchByAddressRequest {
        private String address;

        @Builder
        public SearchByAddressRequest(String address) {
            this.address = address;
        }

        // request 데이터를 DB에 처리 하기 위해 dto를 entity로 변환하기 위한 메소드
        public ChargerEntity toEntity() {
            return ChargerEntity.builder()
                    .address(address)
                    .build();
        }
    }

    // Client에게 response를 전달하기 위한 dto
    @Getter
    public static class Response {
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

        // DB에서 꺼내온 Entity를 Dto로 변환하여 Client에 전달하기 위한 메소드
        public Response(ChargerEntity charger) {
            this.id = charger.getId();
            this.chargerName = charger.getChargerName();
            this.chargerLocation = charger.getChargerLocation();
            this.city = charger.getCity();
            this.closedDates = charger.getClosedDates();
            this.fastChargeType = charger.getFastChargeType();
            this.slowNum = charger.getSlowNum();
            this.fastNum = charger.getFastNum();
            this.parkingFee = charger.getParkingFee();
            this.lat = charger.getLat();
            this.lon = charger.getLon();
            this.address = charger.getAddress();
        }
    }
}
