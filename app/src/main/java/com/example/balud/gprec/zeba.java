package com.example.balud.gprec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zeba extends AppCompatActivity {

    ListView listView;
    private static InputStream is = null;
    private static String json = "";
    Intent intent;
    public static String zsa;
    public String URL;
    public static String sqw, we, qwe, zeba;
    TextView textView;
    private ProgressDialog pDialog;
    //private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeba);
        listView=(ListView)findViewById(R.id.listView2);
        textView=(TextView)findViewById(R.id.textView97);
        //mAdView = (AdView)findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        //AdRequest adRequest = new AdRequest.Builder()
          //      .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            //    .build();

        // Start loading the ad in the background.
       // mAdView.loadAd(adRequest);
        acceswebservice();
        intent = getIntent();
        zsa = intent.getStringExtra("zeba");
    }

    private void acceswebservice() {
        JsonReadTask task = new JsonReadTask();
        task.execute();
    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(zeba.this);
            pDialog.setMessage("Getting circular Details...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            URL = "http://student.gprec.ac.in/student/circular" + "/" + zsa;
            HttpGet httpGet = new HttpGet(URL);
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
                sqw = jsonObj.getString("title");
                we = jsonObj.getString("details");

                qwe = jsonObj.getString("timestamp");

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
        try {
            JSONObject zeb = new JSONObject(json);

            HashMap<String, String> map = new HashMap<>();
            String ve = zeb.getString("details");
            String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

            Pattern p = Pattern.compile(URL_REGEX);
            Matcher m = p.matcher(ve);//replace with string to compare
            if (m.find()) {
                System.out.println("String contains URL");
                textView.setText(Html.fromHtml(ve));
                textView.setAutoLinkMask(Linkify.WEB_URLS);
            } else {

                String zeba = zeb.getString("details") + "\n" + zeb.getString("timestamp");
                map.put("zeba", zeba);
                arl.add(map);


                SimpleAdapter s = new SimpleAdapter(zeba.this, arl, android.R.layout.simple_list_item_2, new String[]{"zeba"}, new int[]{android.R.id.text2});
                listView.setAdapter(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
