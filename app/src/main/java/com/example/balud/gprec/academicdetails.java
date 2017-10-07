package com.example.balud.gprec;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

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

/**
 * Created by balud on 18/3/16.
 */
public class academicdetails extends Fragment {
    private static InputStream is = null;
    private static JSONObject jsonObj ;
    private static String json = "";
    private static String ze;
    private static String zeba,guru,gurze,guruzeba,venkyjyo,maheshrani,mad,vm,balajisounadrya;
    private static String LOGIN_URL = "http://student.gprec.ac.in/student/mark";
    private TextView e1;
    private TextView e2;
    private TextView e3;
    private TextView e4;
    private TextView e5;
    private TextView e6;
    private TextView e7;
    private TextView e8;
    private TextView e9;
    private static String pavansubbu;
    private static String subbupavan;
    ConnectionDetector cd;
    SessionManager sessionManager;
    //private AdView mAdView;

    ImageButton i10;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.academicdetails,container,false);
        i10=(ImageButton)v.findViewById(R.id.imageButton10);
        e1=(TextView)v.findViewById(R.id.textView39);
        e2=(TextView)v.findViewById(R.id.textView40);
        e3=(TextView)v.findViewById(R.id.textView41);
        e4=(TextView)v.findViewById(R.id.textView42);
        e5=(TextView)v.findViewById(R.id.textView43);
        e6=(TextView)v.findViewById(R.id.textView44);
        e7=(TextView)v.findViewById(R.id.textView45);
        e8=(TextView)v.findViewById(R.id.textView46);
        e9=(TextView)v.findViewById(R.id.textView57);
        //mAdView = (AdView)v.findViewById(R.id.ad_view);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."

        cd = new ConnectionDetector(getActivity());
        sessionManager=new SessionManager(getActivity());
        Bundle args=getArguments();
        pavansubbu=args.getString("user");
        subbupavan=args.getString("pass");
        acceswebservice();
        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();

            }
        });
        return v;
}
    private void acceswebservice()
    {
        JsonReadTask task = new JsonReadTask();
        task.execute();
    }
    private class JsonReadTask extends AsyncTask<String,String,String> {



        @Override
        protected String doInBackground(String... params) {
            String URL=LOGIN_URL+"/"+pavansubbu+"/"+subbupavan;
            DefaultHttpClient httpClient=new DefaultHttpClient();
            HttpGet httpGet=new HttpGet(URL);
            try {
                HttpResponse httpResponse=httpClient.execute(httpGet);
                HttpEntity httpEntity=httpResponse.getEntity();
                is=httpEntity.getContent();
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
                jsonObj = new JSONObject(json);
            } catch (JSONException ignored) {
            }
            JSONObject json=jsonObj;
            try {
                ze=json.getString("gpa_1");
                zeba=json.getString("gpa_12");
                guruzeba=json.getString("gpa_2");
                balajisounadrya=json.getString("gpa_22");
                maheshrani=json.getString("gpa_3");
                venkyjyo=json.getString("gpa_32");
                mad=json.getString("gpa_4");
                vm=json.getString("gpa_42");
                gurze=json.getString("cgpa");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String String) {
            e1.setText(ze);
            e2.setText(zeba);
            e3.setText(guruzeba);
            e4.setText(balajisounadrya);
            e5.setText(maheshrani);
            e6.setText(venkyjyo);
            e7.setText(mad);
            e8.setText(vm);
            e9.setText(gurze);

        }



    }
}
