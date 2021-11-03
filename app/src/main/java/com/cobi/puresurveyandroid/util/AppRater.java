package com.cobi.puresurveyandroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.app.Dialog;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.RatingRequestAnalytics;
import com.cobi.puresurveyandroid.model.RatingRequest;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;

public class AppRater {
    private final static String APP_TITLE = "OM Advisor";// App Name

    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 5;//Min number of launches

    private static SharedPreferences.Editor editor;

//    public static void app_launched(Context mContext) {
//        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
//        if (prefs.getBoolean("dontshowagain", false)) {
//            return;
//        }
//
//        editor = prefs.edit();
//
//        // Increment launch counter
//        long launch_count = prefs.getLong("launch_count", 0) + 1;
//        editor.putLong("launch_count", launch_count);
//
//        // Get date of first launch
//        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
//        if (date_firstLaunch == 0) {
//            date_firstLaunch = System.currentTimeMillis();
//            editor.putLong("date_firstlaunch", date_firstLaunch);
//        }
//
//        //        // Wait at least n days before opening
//        //        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
//        //            if (System.currentTimeMillis() >= date_firstLaunch +
//        //                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
//        //                showRateDialog(mContext, editor);
//        //            }
//        //        }
//
//        showRateDialog(mContext, editor);
//
//        editor.commit();
//    }

//    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
//        final Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
//
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View mView = inflater.inflate(R.layout.rating_dialog, null);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            mView.setClipToOutline(true);
////        }
//
//
//        TextView b1 = mView.findViewById(R.id.rate);
//        TextView b2 = mView.findViewById(R.id.remind);
//        ImageView close = mView.findViewById(R.id.close);
//
//        //        Button b3 = mView.findViewById(R.id.cancel);
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        b1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                //open up rating star dialog
//
//                ShowRatingStarDialog(mContext);
//
//                dialog.dismiss();
//            }
//        });
//
//        b2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//        dialog.setCancelable(false);
//
//        dialog.setContentView(mView);
//        dialog.show();
//
//        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));
//
//    }

    public static void ShowRatingStarDialog(final Context mContext, final int PageType) {

        final Dialog dialog = new Dialog(mContext, R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mView = inflater.inflate(R.layout.star_rating_dialog, null);

        ImageView close = mView.findViewById(R.id.close);
        TextView submit = mView.findViewById(R.id.submit);
        final EditText editText = mView.findViewById(R.id.rating_comment);
        final RatingBar stars = mView.findViewById(R.id.ratingBar);
        final LinearLayout feedback = mView.findViewById(R.id.feedback_section);
        final TextView topTitle = mView.findViewById(R.id.topText);
        final TextView subTitle = mView.findViewById(R.id.subTitle);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnalyticsController.PostRating(mContext, new RatingRequestAnalytics(PreferencesHelper.getCommonName(mContext), (int) stars.getRating(), editText.getText().toString(), PageType, ""));

                feedback.setVisibility(View.GONE);

                topTitle.setText("Rating sent.");
                subTitle.setText("Thank you for rating the app. Your feedback is\n" + " extremely valuable to us.");
            }
        });

        dialog.setCancelable(false);

        dialog.getWindow().getDecorView().setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(mView);
        dialog.show();


        //        final AlertDialog.Builder popDialog = new AlertDialog.Builder(mContext);
        //
        //        LinearLayout linearLayout = new LinearLayout(mContext);
        //        final RatingBar rating = new RatingBar(mContext);
        //
        //        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        //                LinearLayout.LayoutParams.WRAP_CONTENT,
        //                LinearLayout.LayoutParams.WRAP_CONTENT
        //        );

        //        rating.setLayoutParams(lp);
        //        rating.setNumStars(5);
        //        rating.setStepSize(1);
        //
        //        //add ratingBar to linearLayout
        //        linearLayout.addView(rating);
        //
        //        popDialog.setTitle("Add Rating: ");
        //
        //        popDialog.setCancelable(false);
        //
        //        //add linearLayout to dailog
        //        popDialog.setView(linearLayout);
        //
        //
        //        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
        //            @Override
        //            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        //                System.out.println("Rated val:"+v);
        //            }
        //        });
        //
        //
        //        // Button OK
        //        popDialog.setPositiveButton(android.R.string.ok,
        //                new DialogInterface.OnClickListener() {
        //                    public void onClick(DialogInterface dialog, int which) {
        //                        //                        textView.setText(String.valueOf(rating.getProgress()));
        //
        //
        //                        //post rating
        //
        //                        //dont show rating again
        //
        //                        //                        if (editor != null) {
        //                        //                            editor.putBoolean("dontshowagain", true);
        //                        //                            editor.commit();
        //                        //                        }
        //
        //
        //                        dialog.dismiss();
        //                    }
        //
        //                })
        //
        //                // Button Cancel
        //                .setNegativeButton("Cancel",
        //                        new DialogInterface.OnClickListener() {
        //                            public void onClick(DialogInterface dialog, int id) {
        //                                dialog.cancel();
        //                            }
        //                        });
        //
        //        popDialog.create();
        //        popDialog.show();

    }
}
