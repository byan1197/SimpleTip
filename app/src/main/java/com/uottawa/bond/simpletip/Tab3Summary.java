package com.uottawa.bond.simpletip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Bond on 2017-05-17.
 */

public class Tab3Summary extends Fragment {

    TextView billTotalTextV, grandTotalTextV, tipTextV,tipPerPersonTextV;
    LinearLayout container;

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
        tipPerPersonTextV = (TextView) view.findViewById(R.id.tipPPText);

        container = (LinearLayout) view.findViewById(R.id.tpp_container);

        System.out.println(billTotalTextV);
    }

    public void updateInfo (double bill, double tip, int ppl){
        double dppl = ppl;
        if (ppl <= 1) {
            dppl = 1.0;
            container.setVisibility(GONE);
        }
        else
            container.setVisibility(VISIBLE);


        double totalTipAmount =(tip * bill);
        double tipAmountPerPerson = (tip * bill) / dppl;
        double grandAmount = totalTipAmount + bill;
        billTotalTextV.setText(String.format("$%.2f", bill));
        tipTextV.setText(String.format("$%.2f", totalTipAmount));
        tipPerPersonTextV.setText(String.format("$%.2f", tipAmountPerPerson));
        grandTotalTextV.setText(String.format("$%.2f", grandAmount));
    }
}
