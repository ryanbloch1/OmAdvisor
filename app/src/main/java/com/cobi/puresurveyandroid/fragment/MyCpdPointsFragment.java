package com.cobi.puresurveyandroid.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.databinding.FragmentMyCpdPointsBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.Cpd;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.MyCpdData;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.PlumlineApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyCpdPointsFragment extends BaseFragment {



    private FragmentMyCpdPointsBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.CpdAccessed.getNumVal(), ""));

        // Inflate the layout for this fragment
        mBinding =   DataBindingUtil.inflate(inflater, R.layout.fragment_my_cpd_points, container, false);

        PlumlineApiController.getUserCpd(getContext(), PreferencesHelper.getSalesCode(getContext()));


        mBinding.progress.setVisibility(View.VISIBLE);


        return mBinding.getRoot();

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(MyCpdData result) {


        mBinding.setData(result.getData().getCpd());


        mBinding.progress.setVisibility(View.GONE);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.progress.setVisibility(View.GONE);

        if (errorResponse.getRequest().equals(PlumlineApiController.CPD)) {

            mBinding.setError("No CPD points found for this user");
        }
    }

}
