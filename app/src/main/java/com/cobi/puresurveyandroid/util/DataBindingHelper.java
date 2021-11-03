package com.cobi.puresurveyandroid.util;

import androidx.databinding.BindingAdapter;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.Event;
import com.cobi.puresurveyandroid.model.SingleEvent;
import com.cobi.puresurveyandroid.view.RoundGraph;
import com.cobi.puresurveyandroid.view.RoundGraphCpd;
import com.cobi.puresurveyandroid.view.RoundGraphIssuedSales;
import com.github.pavlospt.roundedletterview.RoundedLetterView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

public class DataBindingHelper {

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("pageImage")
    public static void setPageImage(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            // TODO: Adding in a hack to force HTTPS due to Picasso not loading insecure images
            imageUrl = imageUrl.replaceAll("(http://)", "https://");
            Picasso.get().load(imageUrl).into(imageView);
        }
    }

    @BindingAdapter("roundedCornersImage")
    public static void setRoundedCorners(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            try {
                Field idField = R.drawable.class.getDeclaredField(imageUrl);
                imageView.setImageResource(idField.getInt(idField));
            } catch (Exception e) {
                e.printStackTrace();
                imageView.setImageBitmap(null);
            }
        }
    }

    @BindingAdapter("currency")
    public static void setCurrency(RoundGraph roundGraph, String currency) {
        if (currency != "") {
            roundGraph.setCurrency(currency);
        }
    }

    @BindingAdapter({"current", "target"})
    public static void setShortfall(RoundGraph roundGraph, double current, double target) {
        if (current != 0 && target != 0) {
            roundGraph.setCommission(current);
            roundGraph.setTotal(100f);
            roundGraph.setValue((current / target) * 100);

            if (current >= target) {
                roundGraph.setShortfall(0);
            } else {
                roundGraph.setShortfall(target - current);
            }
        } else {
            roundGraph.setCommission(0);
            roundGraph.setTotal(0);
            roundGraph.setValue(0);
            if (target > 0) {
                roundGraph.setShortfall(target - current);
            } else {
                roundGraph.setShortfall(0);
            }
        }
    }



    @BindingAdapter("currency")
    public static void setCurrencyIssued(RoundGraphIssuedSales roundGraph, String currency) {
        if (currency != "") {
            roundGraph.setCurrency(currency);
        }
    }

    @BindingAdapter({"current", "target"})
    public static void setShortfallIssued(RoundGraphIssuedSales roundGraph, double current, double target) {
        if (current != 0 && target != 0) {
            roundGraph.setCommission(current);
            roundGraph.setTotal(100f);
            roundGraph.setValue((current / target) * 100);

            if (current >= target) {
                roundGraph.setShortfall(0);
            } else {
                roundGraph.setShortfall(target - current);
            }
        } else {
            roundGraph.setCommission(0);
            roundGraph.setTotal(0);
            roundGraph.setValue(0);
            if (target > 0) {
                roundGraph.setShortfall(target - current);
            } else {
                roundGraph.setShortfall(0);
            }
        }
    }

    @BindingAdapter({"productCheck"})
    public static void setReChecked(CheckBox checkBox, String value) {

        if(value!=null){
            if(value.equals("0")){
                checkBox.setChecked(false);
            }
            else{
                checkBox.setChecked(true);
            }
        }
    }





    @BindingAdapter({"current", "target"})
    public static void setCpdGraph(RoundGraphCpd roundGraph, double current, double target) {
        if (current != 0 && target != 0) {
            roundGraph.setAttained(current);
            roundGraph.setRequired(target);

            roundGraph.setTotal(100f);
            roundGraph.setValue((current / target) * 100);

        } else {
            roundGraph.setAttained(0);
            roundGraph.setRequired(0);
            roundGraph.setTotal(0);
            roundGraph.setValue(0);
        }
    }




    @BindingAdapter("roundedLetterView")
    public static void setRoundedLetter(RoundedLetterView letterView, int value) {
        if (letterView != null && letterView.getContext() != null) {
            letterView.setTitleText(String.valueOf(value));
            letterView.setBackgroundColor(letterView.getContext().getResources().getColor(R.color.recorder_bg));
        }
    }

    @BindingAdapter("date")
    public static void setDate(TextView textView, String date) {
        if (textView != null && textView.getContext() != null) {
            textView.setText(DateHelper.dateToSimpleDate(DateHelper.ISO8601ToDate(date)));
        }
    }

    @BindingAdapter("day")
    public static void setDay(TextView textView, String date) {
        if (textView != null && textView.getContext() != null) {
            textView.setText(DateHelper.getDay(DateHelper.ISO8601ToDate(date)));
        }
    }

    @BindingAdapter("month")
    public static void setMonth(TextView textView, String date) {
        if (textView != null && textView.getContext() != null) {
            textView.setText(DateHelper.getMonth(DateHelper.ISO8601ToDate(date)));
        }
    }

    @BindingAdapter({"start", "end"})
    public static void setTime(TextView textView, String start, String end) {
        if (textView != null && textView.getContext() != null) {
            textView.setText(DateHelper.getTime(DateHelper.ISO8601ToDate(start)) + " - " + DateHelper.getTime(DateHelper.ISO8601ToDate(end)));
        }
    }

    @BindingAdapter({"dateTime"})
    public static void setDateTime(TextView textView, String date) {
        if (textView != null && textView.getContext() != null) {
            textView.setText(DateHelper.getDay(DateHelper.ISO8601ToDate(date)) + ' ' + DateHelper.getMonth(DateHelper.ISO8601ToDate(date)) + ' ' + DateHelper.getTime(DateHelper.ISO8601ToDate(date)));
        }
    }

    @BindingAdapter({"eventMainButton"})
    public static void setEventButtonText(Button button, SingleEvent event) {

        if (event != null) {

            if (event.getRegistrationStatus() == 1) { //person has registered.   button will only show check-in or checkout

                if (event.isUpcoming() || event.getHasCheckedOut()) {

                    button.getBackground().setAlpha(128);
                } else {

                    button.getBackground().setAlpha(255);
                }

                if (event.getCheckedIn()) {
                    button.setText("check out");
                } else {
                    button.setText("check in");
                }
            } else {
                if (event.getRegistrationStatus() == 2) {  //person has'nt registered
                    button.setText("register");
                } else if (event.getRegistrationStatus() == 4) { //person has declined
                    button.setText("declined");
                    button.getBackground().setAlpha(128);
                }
            }
        }
    }

    @BindingAdapter({"eventButton"})
    public static void setEventRowButtonText(Button button, Event event) {

        if (event != null) {
            if (event.getRegistrationStatus() == 1) { //person has registered.   button will only show check-in or checkout
                button.getBackground().setAlpha(255);
                if (event.getCheckedIn()) {
                    button.setText("check out");
                } else {
                    button.setText("check in");
                }
            } else {
                if (event.getRegistrationStatus() == 2) {  //person has'nt registered
                    button.setText("register");
                } else if (event.getRegistrationStatus() == 4) { //person has declined
                    button.setText("declined");
                    button.getBackground().setAlpha(128);
                }
            }
        }
    }
}