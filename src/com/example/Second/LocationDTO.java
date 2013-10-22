package com.example.Second;

import java.io.Serializable;

public class LocationDTO implements Serializable {
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
