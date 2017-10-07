package com.example.balud.gprec;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class circulars extends AppCompatActivity {
    ListView listView;
    private static InputStream is = null;
    private static String json = "";
    private static JSONArray ze;
    ConnectionDetector cd;
    SpecialAdapter specialAdapter;
    private ProgressDialog pDialog;
    //private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circulars);
        listView=(ListView)findViewById(R.id.listView);
        Context context=getApplicationContext();
        cd = new ConnectionDetector(getApplicationContext());


        //setCallback(ProgressCallback);
        Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

        if (isInternetPresent) {
            acceswebservice();
        }
        else {
            Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject za = ze.getJSONObject(position);
                    String aw = za.getString("id");
                    Intent intent = new Intent(circulars.this, zeba.class);
                    intent.putExtra("zeba", aw);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void acceswebservice() {
        JsonReadTask task = new JsonReadTask();
        task.execute();
    }
    private class JsonReadTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(circulars.this);
            pDialog.setMessage("Getting circulars...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String LOGIN_URL = "http://student.gprec.ac.in/student/circular/";
            HttpGet httpGet = new HttpGet(LOGIN_URL);
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder str = new StringBuilder();
                String strLine;
                while ((strLine = reader.readLine()) != null) {
                    str.append(strLine).append("\n");
                }
                is.close();
                json = str.toString();
            } catch (Exception ignored) {
            }
            try {
                JSONObject jsonObj = new JSONObject(json);
            } catch (JSONException ignored) {
            }

            return json;

        }
        protected void onPostExecute(String String) {
            pDialog.dismiss();
            draw();
        }

    }
    public void draw()
    {
        ArrayList<HashMap<String, String>> arl = new ArrayList<>();
        try
        {
            JSONObject zeb=new JSONObject(json);
            ze=zeb.getJSONArray("records");
            for(int i=0;i<ze.length();i++){
                HashMap<String,String> map=new HashMap<>();
                JSONObject zs=ze.getJSONObject(i);
                String zeba = /*zs.getString("id") + "\n" +*/ zs.getString("title") + "\n" + zs.getString("timestamp");
                map.put("zeba",zeba);
                arl.add(map);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        specialAdapter=new SpecialAdapter(circulars.this,arl,android.R.layout.simple_list_item_2,new String[]{"zeba"},new int[]{android.R.id.text2});
        listView.setAdapter(specialAdapter);

    }

}
