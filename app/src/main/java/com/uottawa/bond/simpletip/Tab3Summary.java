package com.uottawa.bond.simpletip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Bond on 2017-05-17.
 */

public class Tab3Summary extends Fragment {

    TextView billTotalTextV, grandTotalTextV, tipTextV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3summary, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        billTotalTextV = (TextView) view.findViewById(R.id.billTexttv);
        tipTextV = (TextView) view.findViewById(R.id.tipText);
        grandTotalTextV = (TextView) view.findViewById(R.id.grandText);

        System.out.println(billTotalTextV);
    }

    public void updateInfo (double bill, double tip, int ppl){
        double tipAmount = (tip * bill) / (double)ppl;
        double grandAmount = tipAmount + bill;
        billTotalTextV.setText(String.format("$%.2f", bill));
        tipTextV.setText(String.format("$%.2f", tipAmount));
        grandTotalTextV.setText(String.format("$%.2f", grandAmount));
    }
}
