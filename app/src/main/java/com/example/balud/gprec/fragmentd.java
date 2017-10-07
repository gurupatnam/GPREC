package com.example.balud.gprec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;

/**
 * Created by balud on 19/3/16.
 */
public class fragmentd extends Fragment {
    private AdView mAdView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragmentd, container, false);

        return v;
    }
}
