package com.uottawa.bond.simpletip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Bond on 2017-05-17.
 */

public class Tab1Settings extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1settings, container, false);
        return rootView;
    }

}
