package com.cobi.puresurveyandroid.fragment;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.CampaignsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentLeadsBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.PageTypes;
import com.cobi.puresurveyandroid.model.Campaign;
import com.cobi.puresurveyandroid.model.CampaignResponse;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.util.cPriority;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

public class CampaignsFragment extends BaseFragment {

    private static final String TAG = CampaignsFragment.class.getSimpleName();
    private static final String EXTRA_LEADS = "LEADS";

    private FragmentLeadsBinding mBinding;
    private CampaignsAdapter mAdapter;

    public CampaignsFragment() {
        // Required empty public constructor
    }



    public static CampaignsFragment newInstance() {
        return new CampaignsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.LeadsAccessed.getNumVal(), ""));

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_leads, container, false);
        mBinding.progress.setVisibility(View.VISIBLE);
        return mBinding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();

        registerEventBus();
        CSIApiController.getCampaigns(getContext(), PreferencesHelper.getSalesCode(getContext()));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterEventBus();
    }

    private void populateAdapter(List<Campaign> result){

        mAdapter = new CampaignsAdapter(getActivity(), result);
        mBinding.campaignList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.campaignList.setHasFixedSize(true);
        mBinding.campaignList.setAdapter(mAdapter);
        mBinding.progress.setVisibility(View.GONE);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(CampaignResponse response) {
        List<Campaign> result = response.getCampaigns();

        mBinding.setError(null);

        for(Campaign campaign : result) {
            if (campaign.getPriority().equals("Hot")) {
                campaign.setcPriority(cPriority.HOT);
            } else if (campaign.getPriority().equals("High")) {
                campaign.setcPriority(cPriority.HIGH);
            } else if (campaign.getPriority().equals("Medium")) {
                campaign.setcPriority(cPriority.MEDIUM);
            } else {
                campaign.setcPriority(cPriority.LOW);
            }
        }
        Collections.sort(result, Campaign.pComparator);

        populateAdapter(result);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        if(errorResponse.getRequest().equals(CSIApiController.CUSTOMER_GET_CAMPAIGNS)) {
            mBinding.setError("Sorry you have no campaigns");

            populateAdapter(null);
        }
    }

}