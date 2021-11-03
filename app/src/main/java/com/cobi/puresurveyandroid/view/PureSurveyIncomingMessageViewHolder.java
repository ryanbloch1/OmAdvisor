package com.cobi.puresurveyandroid.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessagesList;

/**
 * Created by admin on 2017/10/11.
 */

public class PureSurveyIncomingMessageViewHolder extends MessageHolders.IncomingTextMessageViewHolder<IMessage> {

    private WebView webview;

    public PureSurveyIncomingMessageViewHolder(final View itemView) {
        super(itemView);

        webview = itemView.findViewById(R.id.webview);
        bubble = itemView.findViewById(R.id.bubble);

        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                view.setBackgroundColor(ResourceHelper.getColour(R.color.transparent));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                } else {
                    view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                }

                webview.setVisibility(View.VISIBLE);
                if (PureSurveyApplication.messagesInThread == 1) {
                    findScrollViewAndScrollToTop(view.getParent());
                }

                bubble.setVisibility(View.VISIBLE);
                webview.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // return true; // will disable all links

                // disable phone and email links
                if (url.startsWith("mailto")) {
                    String emailAddress = url.replace("mailto:", "");
                    composeEmail(emailAddress);
                    return true;
                } else if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    webview.getContext().startActivity(intent);
                }

                // leave the decision to the webview
                return false;
            }
        });

        webview.setBackgroundResource(android.R.color.transparent);
        webview.setVisibility(View.GONE);
    }

    private void composeEmail(String emailAddress) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        try {
            webview.getContext().startActivity(Intent.createChooser(intent, webview.getContext().getString(R.string.send_mail_title)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(webview.getContext(), webview.getContext().getString(R.string.no_email_clients_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBind(final IMessage message) {

        super.onBind(message);

        webview.setLayerType(View.LAYER_TYPE_NONE, null);

        webview.loadDataWithBaseURL("file:///android_asset/", getStyledFont(message.getText()), "text/html", "utf-8", null);

        text.setText("");

    }

    private String getStyledFont(String html) {
        boolean addBodyStart = !html.toLowerCase().contains("<body>");
        boolean addBodyEnd = !html.toLowerCase().contains("</body");
        return "<style type=\"text/css\">@font-face {font-family: MontserratLight;" +
                "src: url(\"fonts/montserrat_light.otf\")}" +
                "body {font-family: MontserratLight;font-size: light;text-align: justify; color: #fff;}</style>" +
                (addBodyStart ? "<body>" : "") + html + (addBodyEnd ? "</body>" : "");
    }

    private void findScrollViewAndScrollToTop(final ViewParent parent) {
        if (parent == null) {
            return;
        }
        if (parent instanceof MessagesList) {
            ((MessagesList) parent).postDelayed(new Runnable() {
                @Override
                public void run() {

                    ((MessagesList) parent).smoothScrollBy(0, -6000, new Interpolator() {
                        @Override
                        public float getInterpolation(float v) {
                            return 1;
                        }
                    });
                }
            }, 100);
        } else {
            findScrollViewAndScrollToTop(parent.getParent());
        }
    }
}
