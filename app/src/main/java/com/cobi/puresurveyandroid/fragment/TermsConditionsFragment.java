package com.cobi.puresurveyandroid.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.util.FBRemoteConfigHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;

public class TermsConditionsFragment extends BaseFragment {

    public static TermsConditionsFragment newInstance() {
        return new TermsConditionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.TAndCAccessed.getNumVal(), ""));

        View view = inflater.inflate(R.layout.terms_conditions, container, false);

        final WebView mWebView = view.findViewById(R.id.webview);
        final View progressBar = view.findViewById(R.id.progress);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("mailto:")) {
                    sendEmail(Uri.parse(url).toString().replace("mailto:", ""), false,ActionTypes.TAndCEmailContacted );
                    return true;
                } else {
                    view.loadUrl(url);
                }

                return true;
            }

            @Override
            public void onPageFinished(final WebView view, String url) {

                progressBar.setVisibility(View.GONE);
            }
        });

        String termsUrl = FBRemoteConfigHelper.getTermsAndConditionsUrl();
        mWebView.loadUrl(termsUrl);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }
}
