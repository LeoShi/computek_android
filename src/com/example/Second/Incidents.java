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

    public void btn_break_in(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("House Break in");
    }

    public void btn_dv(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Domestic Violence");
    }

    public void btn_hijack(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Hijack");

    }

    public void btn_suspect(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Suspects");

    }

    public void btn_animal_abuse(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Animal abuse");

    }

    public void btn_shooting(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Shooting");

    }

    public void btn_murder(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Murder");

    }

    public void btn_illegal_firearm(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Illegal Firearm");

    }

    public void btn_drug_dealing(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Drug Dealing");

    }

    public void btn_drug_use(View view) {
        new IncidentReporter(this, getLocationDTO()).execute("Drug Use");

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