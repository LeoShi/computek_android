package com.example.Second;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Incidents extends Activity {
    private LocationDTO locationDTO;
    private AsyncTask<Object, Integer, LocationDTO> locationTask;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incidents);
        LocationProvider locationProvider = new LocationProvider(this);
        locationTask = locationProvider.execute();
    }

    private LocationDTO getLocationDTO() {
        if (locationDTO == null) {
            try {
                locationDTO = locationTask.get();

            } catch (Exception e) {
                Log.e("LocationError", e.getMessage());
            }
        }
        return locationDTO;
    }


    public void btn_abduction(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Abduction");
    }

    public void btn_armed_robbery(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Armed Robbery");
    }

    public void btn_arson(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Arson");
    }

    public void btn_assault(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Assault");
    }

    public void btn_dv(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Domestic Violence");
    }

    public void btn_attempted_murder(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Attempted Murder");
    }

    public void btn_bombing(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Bombing");
    }

    public void btn_terror_attack(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Terror Attack");
    }

    public void btn_terror_suspect(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Terror Suspect");
    }

    public void btn_fighting(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Fighting");
    }

    public void btn_rape(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Rape");
    }

    public void btn_hijack(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Hijack");
    }

    public void btn_shooting(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Shooting");
    }

    public void btn_murder(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Murder");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}