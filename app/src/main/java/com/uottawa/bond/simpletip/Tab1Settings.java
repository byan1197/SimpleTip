package com.uottawa.bond.simpletip;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
/**
 * Created by Bond on 2017-05-17.
 */

public class Tab1Settings extends Fragment {

    Spinner curSpin, defaultSpin;
    ArrayAdapter<CharSequence> aCur, aDefault;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1settings, container, false);

        //spinners and adapters
        defaultSpin = (Spinner)rootView.findViewById(R.id.defaultTip);
        aDefault = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.tipArray, android.R.layout.simple_spinner_item);
        aDefault.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defaultSpin.setAdapter(aDefault);

        curSpin = (Spinner)rootView.findViewById(R.id.currencySpinner);
        aCur = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.currencyArray, android.R.layout.simple_spinner_item);
        aCur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        curSpin.setAdapter(aCur);

        //Shared Preferences
        SharedPreferences sp = rootView.getContext().getSharedPreferences("defaultValues", rootView.getContext().MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("changed", false);

        //listeners
        defaultSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("tip", position);
                editor.apply();
                editor.putBoolean("changed", true);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        curSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("currency", position);
                editor.apply();
                editor.putBoolean("changed", true);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return rootView;

    }
}
