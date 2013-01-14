package com.example.Second;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainMenu extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void btn_incident_report_click(View view){
        startActivity(new Intent(MainMenu.this, Incidents.class));
    }
}