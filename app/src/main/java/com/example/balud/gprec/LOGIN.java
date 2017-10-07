package com.example.balud.gprec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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

public class LOGIN extends AppCompatActivity {
    private EditText user, pass;
    private static InputStream is = null;
    private static JSONObject jsonObj;
    private static String json = "";
    private static String ze, guz;
    private static String LOGIN_URL = "http://student.gprec.ac.in/student/detail";
    private static String LOGIN = "http://student.gprec.ac.in/student/detail";
    public static String z,g;
    private ProgressDialog pDialog;
    SessionManager sessionManager;
    ConnectionDetector cd;
    private GoogleApiClient client;
    EditText e1,e2;
    //private AdView mAdView;

    ImageButton i,ii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        cd = new ConnectionDetector(getApplicationContext());
        sessionManager=new SessionManager(getApplicationContext());
        e1.setText(z);
        e2.setText(g);
        if(cd.isConnectingToInternet()) {
            if (sessionManager.isUserLoggedIn()) {
                Intent intent = new Intent(this, student.class);
                startActivity(intent);
            }
        }
        else {
            Intent intent=new Intent(this,START.class);
            startActivity(intent);
            Toast.makeText(this,"connect to internet",Toast.LENGTH_LONG).show();
        }

        i=(ImageButton)findViewById(R.id.imageButton9);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((user.getText().toString()).length() > 0 && (pass.getText().toString()).length() > 0) {
                    Boolean isInternetPresent = cd.isConnectingToInternet(); // true or false

                    if (isInternetPresent) {
                        z = user.getText().toString().toUpperCase();
                        g = pass.getText().toString();

                        acceswebservice();

                    } else {
                        Toast.makeText(getApplicationContext(), "Connect to internet", Toast.LENGTH_LONG).show();
                    }

                } else {
                    if (!((user.getText().toString()).length() > 0)) {
                        e1.setError("enter rollno");
                    }
                    if (!((pass.getText().toString()).length() > 0)) {
                        e2.setError("enter password");
                    }


                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "loginpage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.balud.gprec/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "loginpage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.balud.gprec/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }



    private void acceswebservice() {
        JsonReadTask task = new JsonReadTask();
        task.execute();
    }


    private class JsonReadTask extends AsyncTask<String, String, String> {


        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LOGIN.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String URL = LOGIN_URL + "/" + z + "/" + g;
            DefaultHttpClient httpClient = new DefaultHttpClient();
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
                jsonObj = new JSONObject(json);
            } catch (JSONException ignored) {
            }
            JSONObject json = jsonObj;

            try {
                String y;
                if ((y = json.getString("error")).equals("ERROR_NO_RECORDS_FOUND")) {
                    guz = "invalid username or password";
                } else {
                    ze = json.getString("rollno");
                    String zeba = json.getString("name");
                    guz="login success";


                    if (ze.equals(z)) {
                        sessionManager.createUserLoginSession(z,g);
                        Intent intent = new Intent(LOGIN.this, student.class);
                        intent.putExtra("guru", LOGIN_URL);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("zeba", z);
                        intent.putExtra("guz", g);
                        startActivity(intent);
                    }
                }
                return guz;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String String) {
            pDialog.dismiss();
            if (String!=null) {
                Toast.makeText(LOGIN.this, String, Toast.LENGTH_LONG).show();
            }

        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Intent it = new Intent(LOGIN.this,START.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }


}
