/*
 * RoundGraph.java
 * medscheme-android
 *
 * Copyright (c) 2017 Medscheme. All rights reserved.
 */

package com.cobi.puresurveyandroid.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.util.NumberFormatter;

public class RoundGraph extends LinearLayout {

    private double total;
    private double value;
    private double commission;
    private double shortfall;


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        if(mShortFallText!=null){
            mShortFallText.setText(NumberFormatter.formatCurrencyNumber(shortfall, currency));
        }

        else if(mCommissionText!=null){
            mCommissionText.setText(NumberFormatter.formatCurrencyNumber(commission, currency));
        }

    }

    private String currency;
    private int default_color;
    private int startProgress_color;
    private int endProgress_color;
    private PieChart mChart;
    private TextView mCommissionText;
    private TextView mShortFallText;

    public RoundGraph(Context context) {
        super(context);
        init();
    }

    public RoundGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public RoundGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // Inflate layout
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_round_graph, this, true);

        mChart = findViewById(R.id.pie);
        mCommissionText = findViewById(R.id.current_commission);
        mShortFallText = findViewById(R.id.shortfall);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
        setGraphValue();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        setGraphValue();
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
        setCommissionValue(commission);
    }

    public double getShortfall() {
        return shortfall;
    }

    public void setShortfall(double shortfall) {
        this.shortfall = shortfall;
        setShortfallValue(shortfall);
    }

    public int getDefault_color() {
        return default_color;
    }

    public void setDefault_color(int default_color) {
        this.default_color = default_color;
        setGraphDefaultColor();
    }

    public int getStartProgress_color() {
        return startProgress_color;
    }

    public void setStartProgress_color(int startProgress_color) {
        this.startProgress_color = startProgress_color;
        setGraphProgressColor();
    }

    public int getEndProgress_color() {
        return endProgress_color;
    }

    public void setEndProgress_color(int endProgress_color) {
        this.endProgress_color = endProgress_color;
        setGraphProgressColor();
    }

    private void setCommissionValue(double commissionValue) {
        if (mCommissionText != null && currency!=null) {
            mCommissionText.setText(NumberFormatter.formatCurrencyNumber(commissionValue, currency));
        }
        else if(currency!=null){
            mCommissionText.setText(NumberFormatter.formatCurrencyNumber(0, currency));
        }
    }

    private void setShortfallValue(double shortfallValue) {
        if (mShortFallText != null && currency!=null) {
            mShortFallText.setText(NumberFormatter.formatCurrencyNumber(shortfallValue, currency));
        }
        else if(currency!=null){
            mShortFallText.setText(NumberFormatter.formatCurrencyNumber(0, currency));
        }
        else{
            mShortFallText.setText("");
        }
    }

    private void setGraphDefaultColor() {
        if (mChart != null && default_color != 0) {
            mChart.setEmptyStrokeColor(default_color);
        }
    }

    private void setGraphProgressColor() {
        if (mChart != null) {
            if (startProgress_color != 0) {
                mChart.setStartStrokeColor(startProgress_color);
            }
            if (endProgress_color != 0) {
                mChart.setEndStrokeColor(endProgress_color);
            }
        }
    }

    private void setGraphValue() {
        if (mChart != null && total != 0) {
            float progress = (float) ((value / total) * 100);
            mChart.setProgress(progress);
        }
    }
}