package com.example.mobileassignment2;

public class DataContainer {

    private long id;

    private String address;
    private double latitude;
    private double longitude;


    public DataContainer(){

    }
    public DataContainer(String address, double latitude,double longitude){
        this.address=address;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
