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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Bond on 2017-05-17.
 */

public class Tab2Home extends Fragment {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    EditText numPeopleEdit, billAmount;
    TextView tipPrompt, servicePrompt, curTextView,percentTextView;
    Button addPpl, subPpl;
    int numPpl;
    RatingBar serviceRate;

    OnDataSetListener odsl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2home, container, false);

        //Spinner and adapter
        spinner = (Spinner)rootView.findViewById(R.id.tipSpinner);
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.tipArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //views
        serviceRate=(RatingBar) rootView.findViewById(R.id.serviceBar);
        tipPrompt = (TextView) rootView.findViewById(R.id.tipSuggestPrompt);
        servicePrompt = (TextView) rootView.findViewById(R.id.serviceRatePrompt);
        numPeopleEdit = (EditText) rootView.findViewById(R.id.numPeople);
        subPpl = (Button) rootView.findViewById(R.id.subPplBtn);
        billAmount = (EditText)rootView.findViewById(R.id.billEditText);
        addPpl = (Button) rootView.findViewById(R.id.addPplBtn);
        curTextView = (TextView) rootView.findViewById(R.id.curText);
        percentTextView = (TextView) rootView.findViewById(R.id.percent);

        //listeners
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    serviceRate.setVisibility(View.VISIBLE);
                    tipPrompt.setVisibility(View.VISIBLE);
                    servicePrompt.setVisibility(View.VISIBLE);
                    percentTextView.setVisibility(View.INVISIBLE);
                }
                else{
                    tipPrompt.setVisibility(View.GONE);
                    servicePrompt.setVisibility(View.GONE);
                    serviceRate.setVisibility(View.GONE);
                    percentTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addPpl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numPpl = Integer.parseInt(String.valueOf(numPeopleEdit.getText()));
                numPpl ++;
                numPeopleEdit.setText(Integer.toString(numPpl));
            }
        });

        subPpl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numPpl = Integer.parseInt(String.valueOf(numPeopleEdit.getText()));
                if (numPpl > 1)
                    numPpl--;
                else
                    numPpl = 1;
                numPeopleEdit.setText(Integer.toString(numPpl));
            }
        });

        return rootView;
    }
    public void setDefaults(int tipPos, int curPos){
        spinner.setSelection(tipPos);
        if (curPos ==0) {
            curTextView.setText("$");
        }
        else if (curPos == 1) {
            curTextView.setText("£");
        }
        else {
            curTextView.setText("€");
        }
    }

    public boolean noData(){
        if (billAmount.getText() == null || String.valueOf(billAmount.getText()).isEmpty() || String.valueOf(billAmount.getText())== "")
            return true;
        return false;
    }

    public void transferInfo(){
        double bill, percentage;
        bill = Double.parseDouble(String.valueOf(billAmount.getText()));
        if (spinner.getSelectedItemPosition()==0)
            percentage = ((double)(serviceRate.getRating()) * 2.0 + 10.0)/100.0;
        else
            percentage = Double.parseDouble(String.valueOf(spinner.getSelectedItem()))/100;

        odsl.setData(bill, percentage, numPpl);
    }

    public interface OnDataSetListener {
        public void setData(double bill, double tip, int ppl);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            odsl = (OnDataSetListener) context;
        } catch (Exception e){}
    }

}
