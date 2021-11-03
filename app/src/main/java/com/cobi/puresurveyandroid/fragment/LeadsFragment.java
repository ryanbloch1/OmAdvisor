package com.cobi.puresurveyandroid.fragment;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobi.puresurveyandroid.PureSurveyApplication;
import com.cobi.puresurveyandroid.R;
import com.cobi.puresurveyandroid.adapter.LeadsAdapter;
import com.cobi.puresurveyandroid.databinding.FragmentLeadsListBinding;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionRequest;
import com.cobi.puresurveyandroid.model.AnalyticsRequests.ActionTypes;
import com.cobi.puresurveyandroid.model.Campaign;
import com.cobi.puresurveyandroid.model.ErrorResponse;
import com.cobi.puresurveyandroid.model.Lead;
import com.cobi.puresurveyandroid.model.LeadResponse;
import com.cobi.puresurveyandroid.model.leadsState;
import com.cobi.puresurveyandroid.util.PreferencesHelper;
import com.cobi.puresurveyandroid.webservice.controller.AnalyticsController;
import com.cobi.puresurveyandroid.webservice.controller.CSIApiController;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeadsFragment extends BaseFragment {

    private FragmentLeadsListBinding mBinding;
    public final String NAME = "LeadsFragment";
    LeadsAdapter leadsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        AnalyticsController.PostAction(getContext(), new ActionRequest(PreferencesHelper.getDeviceId(getContext()), ActionTypes.LeadCampaignAccessed.getNumVal(), String.valueOf(PreferencesHelper.getCampaignId(getContext()))));

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_leads_list, container, false);

        mBinding.progress.setVisibility(View.VISIBLE);

        Context context = getContext();
        if (context != null) {

            CSIApiController.getLeads(context, PreferencesHelper.getSalesCode(context), String.valueOf(PreferencesHelper.getCampaignId(context)));
        }

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


    private void populateAdapter(List<Lead> response) {
        leadsAdapter = new LeadsAdapter(response, getContext());
        mBinding.leadsList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mBinding.leadsList.setHasFixedSize(true);
        mBinding.leadsList.setAdapter(leadsAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessResponse(LeadResponse response) {
        List<Lead> result = response.getLeads();

        if (result.isEmpty()) {
            mBinding.setError("Sorry! Your sales code was not matched on any of our records for the function you have selected.");
        }

        mBinding.progress.setVisibility(View.GONE);


        if (PreferencesHelper.getIsHotLead(getContext())) {
            for (Lead lead : result) {
                if (lead.getCurrentState().equals("New")) {


                    if (!lead.getLeadExpiryDate().equals("")) {
                        lead.setState(leadsState.EXPIRY);
                    } else {
                        lead.setState(leadsState.NOEXPIRY);
                    }

                } else {

                    lead.setState(leadsState.OTHER);

                }

            }

            Collections.sort(result, Lead.pComparator);
        }


        populateAdapter(result);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorResponse(ErrorResponse errorResponse) {
        mBinding.progress.setVisibility(View.GONE);
        if (errorResponse.getRequest().equals(CSIApiController.CUSTOMER_GET_LEADS)) {
            mBinding.setError(errorResponse.getMessage());
        }
    }

}
