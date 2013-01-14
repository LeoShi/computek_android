package com.example.Second;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationWrapper {
    private LocationManager locationManager;
    private final LocationListener locationListener;
    private double latitude;
    private double longitude;
    private boolean isAvailable;

    public LocationWrapper(LocationManager locationManager) {
        this.locationManager = locationManager;
        this.locationListener = initLocationListener();
        this.isAvailable = false;
    }

    private LocationListener initLocationListener(){
        //Start a location listener
        return new LocationListener() {
            public void onLocationChanged(Location loc) {
                //sets and displays the lat/long when a location is provided
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();
                String latlong = "Lat: " + latitude + " Long: " + longitude;
                Log.w("Location", latlong);
                isAvailable = true;
            }

            public void onProviderDisabled(String provider) {
                // required for interface, not used
                Log.w("This is not avaliable", provider);
//                if(provider.equals("gps")){
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//                }
            }

            public void onProviderEnabled(String provider) {
                // required for interface, not used
                Log.w("RRRRRRRRRRRRRRR", "ttttttttttttttt");
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // required for interface, not used
                Log.w("aaaaaaaaaa","I am changed");
            }
        };
    }

    public void startLocation(){
        Log.w("JJJ", "aaa");
        this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener);
    }

    public void stopLocation(){
        this.locationManager.removeUpdates(locationListener);
    }

    public String getLocation(){
        Log.w("getLocation", String.format("Latitude:%f,Longitude:%f",this.latitude,this.longitude));
        String google_reverse_url = String.format("http://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&sensor=true", this.latitude, this.longitude);
        Response response = RequestWrapper.get(google_reverse_url);
        Log.w("reverse:", response.getContent());
        String address = "Johannesburg 2000, South Africa";
        try {
            JSONObject jObject = new JSONObject(response.getContent());
            address = ((JSONObject) jObject.getJSONArray("results").get(0)).getString("formatted_address");
            Log.w("address", address);
        } catch (JSONException e) {
            Log.e("Parse Json Error", e.toString());
        }
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {

        return latitude;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
