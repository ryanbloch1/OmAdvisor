package com.cobi.puresurveyandroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.activity.LoginActivity;
import com.cobi.puresurveyandroid.databinding.TextInputReplyBinding;

import org.w3c.dom.Text;

public class AlertDialogHelper {

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    // User clicked the Yes button

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    // User clicked the No button
                    break;
            }
        }
    };

    public static void showAlertDialog(String error, Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setMessage(error);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (final Exception e) {
            Log.d(AlertDialogHelper.class.getSimpleName(), "showAlertDialog(): Failed.", e);
        }
    }



    public static void showAlertDialogHtml(String error, Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            TextView messageTextView = new TextView(context);
            messageTextView.setText(Html.fromHtml(error));
//            messageTextView.setLinksClickable(true);
            messageTextView.setMovementMethod(LinkMovementMethod.getInstance());



            messageTextView.setPadding(20, 20, 20, 20);

            builder.setView(messageTextView);


            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (final Exception e) {
            Log.d(AlertDialogHelper.class.getSimpleName(), "showAlertDialog(): Failed.", e);
        }
    }


    public static void confirm(String title, final Context context) {
        try {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(context, LoginActivity.class);

                    context.startActivity(intent);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (final Exception e) {
            Log.d("alert ::", "showAlertDialog(): Failed.", e);
        }
    }


    public static void showNewFeatureOverlay(String title, String description, final Context context){

        final Dialog dialog = new Dialog(context, R.style.DialogTheme);

        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(R.layout.new_feature_popup, null);

        ImageView close = mView.findViewById(R.id.close);
        TextView featureTitle = mView.findViewById(R.id.feature_title);
        TextView featureDescription = mView.findViewById(R.id.feature_description);

        featureTitle.setText(title);
        featureDescription.setText(description);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;


        dialog.setCancelable(false);

        dialog.setContentView(mView);
        dialog.show();

        dialog.getWindow().setAttributes(lp);

    }

    /**
     * Shows initial message.
     *
     * @param message Spanned message to be displayed.
     * @param context Application context.
     */
    public static void showMessageDialog(Spanned message, String title, Context context) {
        try {
            TextView messageTextView = new TextView(context);
            messageTextView.setText(message);
            messageTextView.setMovementMethod(LinkMovementMethod.getInstance());
            messageTextView.setPadding(20, 20, 20, 20);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setView(messageTextView);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (final Exception e) {
            Log.d("alert ::", "showAlertDialog(): Failed.", e);
        }
    }
}
