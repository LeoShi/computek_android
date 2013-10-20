package com.example.Second;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationProvider extends AsyncTask<Object, Integer, LocationDTO> {
    ProgressDialog progDailog = null;

    private double latitude = 0.0;
    private double longitude = 0.0;

    private LocationManager mLocationManager;
    private MyLocationListener locationListener;
    private Context context;
    private String address;

    public LocationProvider(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        locationListener = new MyLocationListener();
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(
                getLocationProvider(), 0, 0,
                locationListener);

        progDailog = new ProgressDialog(context);
        progDailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                LocationProvider.this.cancel(true);
            }
        });
        progDailog.setMessage("Loading Location...");
        progDailog.setIndeterminate(true);
        progDailog.setCancelable(false);
        progDailog.show();

    }

    private String getLocationProvider(){
        Criteria myCriteria = new Criteria();
        myCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        myCriteria.setPowerRequirement(Criteria.POWER_LOW);
        return mLocationManager.getBestProvider(myCriteria, true);
//        return LocationManager.NETWORK_PROVIDER;
    }

    @Override
    protected void onCancelled(){
        Log.w("LocationCancelled", "User Cancelled loading location");
        progDailog.dismiss();
        mLocationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onPostExecute(LocationDTO result) {
        mLocationManager.removeUpdates(locationListener);
        progDailog.dismiss();

        Toast.makeText(context,
                String.format("Current Address:%s", result.getAddress()),
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected LocationDTO doInBackground(Object... params) {
        while (this.latitude == 0.0) {
        }
        address = getReverseAddress();
        return new LocationDTO(latitude,longitude,address);
    }

    public String getReverseAddress(){
        Log.w("getReverseAddress", String.format("Latitude:%f,Longitude:%f",this.latitude,this.longitude));
        String google_reverse_url = String.format(context.getString(R.string.google_reverse_url), this.latitude, this.longitude);
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

    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            try {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.w("Latitude and Longitude", Double.toString(latitude)+","+Double.toString(longitude));
                Log.w("Location Provider", location.getProvider());

            } catch (Exception e) {
                Log.e("GetLocation", "Unable to get Location");
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.w("OnProviderDisabled", "OnProviderDisabled");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.w("onProviderEnabled", "onProviderEnabled");
        }

        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            Log.w("onStatusChanged", "onStatusChanged");

        }

    }

}