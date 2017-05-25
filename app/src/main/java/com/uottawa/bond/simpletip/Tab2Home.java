package com.uottawa.bond.simpletip;

import android.app.Activity;
import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bond on 2017-05-17.
 */

public class Tab2Home extends Fragment {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    EditText numPeopleEdit, billAmount;
    TextView tipPrompt, servicePrompt;
    Button addPpl, subPpl;
    int numPpl;
    RatingBar serviceRate;

    OnDataSetListener odsl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("view created");
        View rootView = inflater.inflate(R.layout.tab2home, container, false);

        spinner = (Spinner)rootView.findViewById(R.id.tipSpinner);
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.tipArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        serviceRate=(RatingBar) rootView.findViewById(R.id.serviceBar);
        tipPrompt = (TextView) rootView.findViewById(R.id.tipSuggestPrompt);
        servicePrompt = (TextView) rootView.findViewById(R.id.serviceRatePrompt);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    serviceRate.setVisibility(View.VISIBLE);
                    tipPrompt.setVisibility(View.VISIBLE);
                    servicePrompt.setVisibility(View.VISIBLE);
                }
                else{
                    tipPrompt.setVisibility(View.GONE);
                    servicePrompt.setVisibility(View.GONE);
                    serviceRate.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numPeopleEdit = (EditText) rootView.findViewById(R.id.numPeople);

        billAmount = (EditText)rootView.findViewById(R.id.billEditText);

        addPpl = (Button) rootView.findViewById(R.id.addPplBtn);
        addPpl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numPpl = Integer.parseInt(String.valueOf(numPeopleEdit.getText()));
                numPpl ++;
                numPeopleEdit.setText(Integer.toString(numPpl));
            }
        });

        subPpl = (Button) rootView.findViewById(R.id.subPplBtn);
        subPpl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numPpl = Integer.parseInt(String.valueOf(numPeopleEdit.getText()));
                numPpl --;
                numPeopleEdit.setText(Integer.toString(numPpl));
            }
        });

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        System.out.println("started");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        System.out.println("destroyed");
    }
    @Override
    public void onStop(){
        super.onStop();
        System.out.println("stopped");
    }
    @Override
    public void onPause(){
        super.onPause();
        System.out.println("paused");
    }

    public void transferInfo(){
        double bill, percentage;
        if (billAmount==null || String.valueOf(billAmount.getText())=="0")
            bill = 0.00;
        else
            bill = Double.parseDouble(String.valueOf(billAmount.getText()));

        if (spinner.getSelectedItemPosition()==0)
            percentage = (serviceRate.getNumStars() * 2 + 10)/100;
        else
            percentage = Double.parseDouble(String.valueOf(spinner.getSelectedItem()))/100;

                    odsl.setData(
                            bill,
                            percentage,
                            Integer.parseInt(String.valueOf(numPeopleEdit.getText())));
        System.out.println("info is transferring.");
    }

    public interface OnDataSetListener {
        public void setData(double bill, double tip, int ppl);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        System.out.println("attached");
        try {
            odsl = (OnDataSetListener) context;
        } catch (Exception e){}
    }

}
