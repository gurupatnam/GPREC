package com.example.balud.gprec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class aboutus extends AppCompatActivity {

    ViewPager viewPager=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        viewPager=(ViewPager)findViewById(R.id.pager);
        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new MyAdapter(fragmentManager));


    }

}

class MyAdapter extends FragmentPagerAdapter {


    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Log.d("guru", "getItem: " + position);
        if (position == 0) {
            fragment = new fragmenta();
        }
        if (position == 1) {
            fragment = new fragmentb();
        }
        if (position == 2) {
            fragment = new fragmentc();
        }
        if (position == 3) {
            fragment = new fragmentd();
        }
        if (position == 4) {
            fragment = new fragmente();
        }
        if (position == 5) {
            fragment = new fragmentf();
        }




        return fragment;
    }


    @Override
    public int getCount() {
        Log.d("guru", "getCount: ");
        return 6;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "HISTORY";
        }
        if (position == 1) {
            return "MANAGEMENT";
        }
        if (position == 2) {
            return "ADMINISTRATION";
        }
        if (position == 3) {
            return "PROGRAMS OFFERED";
        }
        if (position == 4) {
            return "AFFILIATION & ACCREDITATION";
        }
        if (position == 5) {
            return "CREDENTIALS";
        }






        return null;
    }


}
