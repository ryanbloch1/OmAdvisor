package com.cobi.puresurveyandroid.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentRateAppBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PageTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.RatingRequestAnalytics;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.util.AlertDialogHelper;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.ResourceHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.PipelinesApiController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RateAppFragment extends BaseFragment implements View.OnClickListener {

    FragmentRateAppBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rate_app, container, false);

        mBinding.commission.setOnClickListener(this);
        mBinding.leads.setOnClickListener(this);
        mBinding.enhancements.setOnClickListener(this);
        mBinding.missedPremiums.setOnClickListener(this);
        mBinding.currentVersion.setOnClickListener(this);
        mBinding.pipeline.setOnClickListener(this);
        mBinding.events.setOnClickListener(this);
        return mBinding.getRoot();
    }

    private void changeButtonStyle(final TextView view) {
        view.setTextColor(ResourceHelper.getColour(R.color.white));
        view.setBackground(ResourceHelper.getDrawable(R.drawable.gradient_button_background));

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                view.setTextColor(ResourceHelper.getColour(R.color.bottomNavigationSelected));
                view.setBackground(ResourceHelper.getDrawable(R.drawable.events_button_background));
            }
        }, 1000);
    }


    @Override
    public void onResume() {
        super.onResume();

        registerEventBus();
    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterEventBus();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.commission:

                ShowRatingStarDialog(getContext(), PageTypes.Commission.getNumVal(), "Commissions page", false);

                changeButtonStyle(mBinding.commission);

                break;

            case R.id.leads:

                ShowRatingStarDialog(getContext(), PageTypes.Leads.getNumVal(), "Leads page", false);

                changeButtonStyle(mBinding.leads);

                break;
            case R.id.pipeline:

                ShowRatingStarDialog(getContext(), PageTypes.Pipelines.getNumVal(), "Pipelines page", false);

                changeButtonStyle(mBinding.pipeline);

                break;

            case R.id.missed_premiums:

                ShowRatingStarDialog(getContext(), PageTypes.MissedPremiums.getNumVal(), "Missed Premiums page", false);

                changeButtonStyle(mBinding.missedPremiums);

                break;
            case R.id.current_version:

                ShowRatingStarDialog(getContext(), PageTypes.General.getNumVal(), "current app version", false);


                break;
            case R.id.enhancements:

                ShowRatingStarDialog(getContext(), PageTypes.Enhancements.getNumVal(), "", true);


                break;
            case R.id.events:

                ShowRatingStarDialog(getContext(), PageTypes.Events.getNumVal(), "Events section", false);


                break;
        }
    }






}
