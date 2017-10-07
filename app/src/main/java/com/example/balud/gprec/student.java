package com.example.balud.gprec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import java.util.HashMap;

public class student extends AppCompatActivity {
    public static String activity;
    public static String z;
    SessionManager se;

    ViewPager viewPager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        viewPager=(ViewPager)findViewById(R.id.pager1);
        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter1(fragmentManager));
        se=new SessionManager(getApplicationContext());
        if(se.checkLogin());
        HashMap<String,String> user=se.getUserDetails();
        activity=user.get(SessionManager.KEY_NAME);
        z=user.get(SessionManager.KEY_PASS);



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
        Intent it = new Intent(student.this,START.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
        finish();
    }

}

class MyAdapter1 extends FragmentPagerAdapter {


    public MyAdapter1(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        String guruzeba=student.activity;
        String zebaguru=student.z;
        Log.d("guru", "getItem: " + position);
        if (position == 0) {
            fragment = new attendance();
            Bundle zbs=new Bundle();
            zbs.putString("user",guruzeba);
            zbs.putString("pass",zebaguru);
            fragment.setArguments(zbs);
        }
        if (position == 1) {
            fragment = new academicdetails();
            Bundle zbs=new Bundle();
            zbs.putString("user",guruzeba);
            zbs.putString("pass",zebaguru);
            fragment.setArguments(zbs);
        }




        return fragment;
    }


    @Override
    public int getCount() {
        Log.d("guru", "getCount: ");
        return 2;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "ATTENDANCE";
        }
        if (position == 1) {
            return "ACADEMIC DETAILS";
        }

        return null;
    }


}
