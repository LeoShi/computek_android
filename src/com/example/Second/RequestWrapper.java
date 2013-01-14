package com.example.Second;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.util.List;

public class RequestWrapper {

    public static Response post(String url, List<NameValuePair> data) {
        HttpPost request = new HttpPost(url);
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        try {
            request.setEntity(new UrlEncodedFormEntity(data));
            return request(request);

        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static Response get(String url)
    {
        return request(new HttpGet(url));
    }

    private static Response request(HttpUriRequest request) {
        HttpResponse response;
        String content = null;
        try {
            response = new DefaultHttpClient().execute(request);
            String http_status_code = response.getStatusLine().toString();
            Log.i("Http Status Code", http_status_code);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream input_stream = entity.getContent();
                content = convertStreamToString(input_stream);
                Log.i("Content", content);
                input_stream.close();
            }
            return new Response(http_status_code, content);

        } catch (Exception e) {
            Log.e("HttpRequest Error:", e.toString());
        }
        return null;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
