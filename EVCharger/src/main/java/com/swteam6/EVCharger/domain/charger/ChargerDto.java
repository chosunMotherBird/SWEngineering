package com.swteam6.EVCharger.domain.charger;

import lombok.*;


@Data
@NoArgsConstructor
public class ChargerDto {

    @Getter
    @NoArgsConstructor
    public static class SearchByAddressRequest {
        private String address;

        @Builder
        public SearchByAddressRequest(String address) {
            this.address = address;
        }

        public ChargerEntity toEntity() {
            return ChargerEntity.builder()
                    .address(address)
                    .build();
        }
    }

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
