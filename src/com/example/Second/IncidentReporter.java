package com.example.Second;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IncidentReporter extends AsyncTask<Void, Integer, Response> {

    private String incidentName;
    private Context context;
    private LocationDTO locationDTO;
    private byte[] pictureData;
    private AlertDialog alertDialog;
    private final long mobile_user_id;

    public IncidentReporter(Context context, String incidentName, LocationDTO locationDTO) {
        this(context, incidentName, locationDTO, null);
    }

    public IncidentReporter(Context context, String incidentName, LocationDTO locationDTO, byte[] pictureData) {
        this.context = context;
        this.incidentName = incidentName;
        this.locationDTO = locationDTO;
        this.pictureData = pictureData;
        this.mobile_user_id = context.getSharedPreferences(context.getString(R.string.user_information), Context.MODE_PRIVATE)
                        .getLong(context.getString(R.string.current_user_id), 0);

    }

    @Override
    protected Response doInBackground(Void... params) {
        Log.w(this.incidentName, locationDTO.getAddress());
        Response response = requestIncident(this.incidentName);
        Log.w("BI_Reponse_status_code", Integer.toString(response.getHttp_status_code()));
        Log.w("BI_Reponse_content", response.getContent());
        return response;
    }

    private Response requestIncident(String incidentName) {
        List<NameValuePair> nameValuePairs = build_incident_info(incidentName);
        return RequestWrapper.post(context.getString(R.string.incidents_url), nameValuePairs);
    }

    private List<NameValuePair> build_incident_info(String incident_name) {
        String location_street = locationDTO.getAddress();
        double latitude = locationDTO.getLatitude();
        double longitude = locationDTO.getLongitude();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("[incident][category]", incident_name));
        nameValuePairs.add(new BasicNameValuePair("[incident][mobile_user_id]", Long.toString(this.mobile_user_id)));
        nameValuePairs.add(new BasicNameValuePair("[incident][location_attributes][latitude]", Double.toString(latitude)));
        nameValuePairs.add(new BasicNameValuePair("[incident][location_attributes][longitude]", Double.toString(longitude)));
        nameValuePairs.add(new BasicNameValuePair("[incident][location_attributes][street]", location_street));
        return nameValuePairs;
    }

    @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage("Sending...");
        alertDialog.show();
    }

    @Override
    protected void onPostExecute(Response response){
        showResult(response);
    }

    private void showResult(Response response) {
        if(response.getHttp_status_code() == 201){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response.getContent());
                String reference = jsonObject.getString("reference");
                display("Successful", "Reference ID is:" + reference);
            } catch (JSONException e) {
                Log.e("Break In", e.toString());
            }
        }else {
            display("Failed", "Please check your network");
        }
    }

    private void display(String title, String message) {
        alertDialog.dismiss();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("OK", (DialogInterface.OnClickListener) null);
        alertDialog.show();
    }
}
