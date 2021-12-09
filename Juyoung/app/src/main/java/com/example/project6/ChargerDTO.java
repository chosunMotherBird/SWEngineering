package com.example.project6;

import androidx.annotation.Nullable;

public class ChargerDTO {
    private int id;
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


    public ChargerDTO(int id, String chargerName, String chargerLocation, String city, String closedDates, String fastChargeType, Integer slowNum, Integer fastNum, String parkingFee, Double lat, Double lon, String address) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChargerName() {
        return chargerName;
    }

    public void setChargerName(String chargerName) {
        this.chargerName = chargerName;
    }

    public String getChargerLocation() {
        return chargerLocation;
    }

    public void setChargerLocation(String chargerLocation) {
        this.chargerLocation = chargerLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClosedDates() {
        return closedDates;
    }

    public void setClosedDates(String closedDates) {
        this.closedDates = closedDates;
    }

    public String getFastChargeType() {
        return fastChargeType;
    }

    public void setFastChargeType(String fastChargeType) {
        this.fastChargeType = fastChargeType;
    }

    public Integer getSlowNum() {
        return slowNum;
    }

    public void setSlowNum(Integer slowNum) {
        this.slowNum = slowNum;
    }

    public Integer getFastNum() {
        return fastNum;
    }

    public void setFastNum(Integer fastNum) {
        this.fastNum = fastNum;
    }

    public String getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(String parkingFee) {
        this.parkingFee = parkingFee;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}

