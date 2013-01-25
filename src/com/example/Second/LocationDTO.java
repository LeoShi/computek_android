package com.example.Second;

public class LocationDTO {
    private final double latitude;
    private final double longitude;
    private final String address;

    public LocationDTO(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
