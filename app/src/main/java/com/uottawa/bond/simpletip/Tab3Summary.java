package com.uottawa.bond.simpletip;

import android.content.SharedPreferences;
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

    TextView billTotalTextV, grandTotalTextV, tipTextV,tipPerPersonTextV,billPerPersonTextV;
    LinearLayout container;
    SharedPreferences sp;
    String currency;
    int curPos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3summary, container, false);

        sp = rootView.getContext().getSharedPreferences("defaultValues", rootView.getContext().MODE_PRIVATE);

        return rootView;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        billTotalTextV = (TextView) view.findViewById(R.id.billTexttv);
        tipTextV = (TextView) view.findViewById(R.id.tipText);
        grandTotalTextV = (TextView) view.findViewById(R.id.grandText);
        tipPerPersonTextV = (TextView) view.findViewById(R.id.tipPPText);
        billPerPersonTextV = (TextView) view.findViewById(R.id.bppTextV);
        container = (LinearLayout) view.findViewById(R.id.perperson_container);

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


        curPos = sp.getInt("currency", 0);
        if (curPos ==0) {
            currency = "$";
        }
        else if (curPos == 1) {
            currency = "£";
        }
        else {
            currency = "€";
        }


        double totalTipAmount =(tip * bill);
        double tipAmountPerPerson = (tip * bill) / dppl;
        double grandAmount = totalTipAmount + bill;
        double billPerPerson = bill/dppl;
        billTotalTextV.setText(String.format(currency+"%.2f", bill));
        tipTextV.setText(String.format(currency+"%.2f", totalTipAmount));
        tipPerPersonTextV.setText(String.format(currency+"%.2f", tipAmountPerPerson));
        grandTotalTextV.setText(String.format(currency+"%.2f", grandAmount));
        billPerPersonTextV.setText(String.format(currency+"%.2f", billPerPerson));
    }
}
